package com.ezetap.android.api.caller.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.android.utils.UIUtils;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.StringUtils;

public class PayCashApiHelper extends ApiHelperBase implements ApiHelper {
	
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		try {
			Intent intent = createIntent();
			
			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
	
			String targetAppPackage = ServiceAppUtils.checkAndDownloadServiceApp(callingActivity, requestCode);
			if (targetAppPackage == null) {
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_PAY_CASH);
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
			
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			if(!StringUtils.hasText(o, EzeConstants.KEY_AMOUNT)) {
				com.ezetap.android.utils.UIUtils.showToast("incorrect_amount", callingActivity);
				return;
			}
			intent.putExtra(EzeConstants.KEY_AMOUNT, o.getDouble(EzeConstants.KEY_AMOUNT));
			if(StringUtils.hasText(o, EzetapConstDef.ADDITIONAL_AMOUNT)) {
				double tip = o.getDouble(EzetapConstDef.ADDITIONAL_AMOUNT);
				if (tip > 0.0) {
					intent.putExtra(EzeConstants.KEY_TIP_AMOUNT, tip);
				}
			}
			if(StringUtils.hasText(o, "__ref")) {
				intent.putExtra(EzeConstants.KEY_ORDERID, o.getString("__ref"));
			}
			if(StringUtils.hasText(o, "mobile")) {
				intent.putExtra(EzeConstants.KEY_CUSTOMER_MOBILE, o.getString("mobile"));
			}
			if(StringUtils.hasText(o, "name")) {
				intent.putExtra(EzeConstants.KEY_CUSTOMER_NAME, o.getString("name"));
			}
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER2)) {
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER2, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER2));
			}
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER3)) {
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER3, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER3));
			}
			if(StringUtils.hasText(o, EzeConstants.KEY_CUSTOMER_EMAIL)) {
				intent.putExtra(EzeConstants.KEY_CUSTOMER_EMAIL, o.getString(EzeConstants.KEY_CUSTOMER_EMAIL));
			}
			if(StringUtils.hasText(o, "_status") && o.getString("_status").contains("Delayed Delivery")) {
				o.put("_reason_code", o.getString("_status"));
				o.put("_reason_id", o.getString("_status_id"));
			}
			o.put("_status", "CASH_PAYMENT");
			o.put("_status_id", "CASH_PAYMENT");
			
			if(StringUtils.hasText(o, "_relation") && o.get("_relation").equals("Self")) {
				o.put("_receiver_name", o.getString("name"));
				o.put("_receiver_mobile", o.getString("mobile"));
			}
			if(StringUtils.hasText(o, EzeConstants.KEY_AMOUNT)) {
				o.put("_amount_paid", o.get(EzeConstants.KEY_AMOUNT));
			}
			if(StringUtils.hasText(intent.getStringExtra(EzeConstants.KEY_ORDERID))) {
				String processCode = EzetapUIContext.getContext().get("loginresponse.defaultProcessCode").toString();
				if(processCode != null && !processCode.equals(""))		
					intent.putExtra(EzeConstants.KEY_PROCESS_CODE, processCode);
				
				JSONObject tempJson = UIUtils.createCopyJSON(o);
				if(tempJson.has("_original_amount")) {
					if(tempJson.getDouble("_original_amount") != tempJson.getDouble(EzeConstants.KEY_AMOUNT)) {
						tempJson.put(EzeConstants.KEY_AMOUNT, o.get("_original_amount"));
					}
					tempJson.remove("_original_amount");
				}
				intent.putExtra(EzeConstants.KEY_PROCESS_DATA_MAP, tempJson.toString());
			}
			
			String validation = com.ezetap.utils.JUtils.isJSONValid(o.toString());
			if(!validation.equalsIgnoreCase("success")) {
				String message = validation + " " + com.ezetap.android.utils.UIUtils.getResourceString("invalid_text", callingActivity);
				if(validation.equalsIgnoreCase("exception")) {
					message = com.ezetap.android.utils.UIUtils.getResourceString("parse_error", callingActivity);
				}
				com.ezetap.android.utils.UIUtils.showToastMessage(message, callingActivity);
				return;
			}
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			intent.putExtra("isCachingEnabled", isCachingEnabled);
	
			callingActivity.startActivityForResult(intent, requestCode);		
		} catch(JSONException e){
			com.ezetap.android.utils.UIUtils.showToastMessage(e.getMessage(), callingActivity);
		}
	}
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
	}
	
	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
	}

}
