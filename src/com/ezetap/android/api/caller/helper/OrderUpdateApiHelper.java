package com.ezetap.android.api.caller.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

public class OrderUpdateApiHelper extends ApiHelperBase implements ApiHelper {

	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		if(o == null) return;
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
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_ORDER_UPDATE);
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());

			JSONObject ad = new JSONObject();
			JSONObject order = new JSONObject();
			JSONObject audit = new JSONObject();
			if(o.has("comment")) {
				audit.put("comment", o.getString("comment"));
			}
			if(o.has("code")) {
				audit.put("code", o.getString("code"));
			}
			
			if(o.has("orderNo")) {
				order.put("orderNo", o.getString("orderNo"));
			} 
			if(o.has("smsCode")) {
				ad.put("smsCode", o.getString("smsCode"));
			}
			if(o.has("smsTemplate")) {
				ad.put("smsTemplate", o.getString("smsTemplate"));
			}			
			order.put("audits", new JSONArray().put(audit));
			ad.put("orders", new JSONArray().put(order));
			
			if(o.has("amountCollected")) {
				ad.put("amount", o.getDouble("amountCollected"));
			}
			
			if(o.has(EzeConstants.KEY_CUSTOMER_MOBILE) && o.get(EzeConstants.KEY_CUSTOMER_MOBILE) != null){
				intent.putExtra(EzeConstants.KEY_CUSTOMER_MOBILE, o.getString(EzeConstants.KEY_CUSTOMER_MOBILE));
				ad.put(EzeConstants.KEY_CUSTOMER_MOBILE, o.getString(EzeConstants.KEY_CUSTOMER_MOBILE));
			}

			ad.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			ad.put(EzeConstants.KEY_PROCESS_CODE, EzetapUIContext.getContext().get("loginresponse.defaultProcessCode"));
			
			intent.putExtra(EzeConstants.KEY_ADDITIONAL_DATA, ad.toString());
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			callingActivity.startActivityForResult(intent, requestCode);
		} catch(JSONException e) {
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
