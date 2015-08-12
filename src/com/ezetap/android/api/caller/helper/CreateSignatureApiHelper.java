package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

public class CreateSignatureApiHelper extends ApiHelperBase implements
		ApiHelper {

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
		
		JSONObject data = new JSONObject();
		data.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
		data.put(EzeConstants.KEY_CUSTOMER_MOBILE, o.get("mobile").toString());
		String receiver = o.getString("_receiver_name");
		if(receiver == null || receiver.equals("")|| receiver.equalsIgnoreCase("null")) receiver = o.getString("name");
		data.put("receiverName", receiver);
		data.put(EzeConstants.KEY_ORDERID, o.get("__ref").toString());
		
		String paymentType = "";
		if(o.getString("_delivery_type").contains("PREPAID") || o.getString("_delivery_type").contains("PP")) {
			paymentType = o.getString("_delivery_type");
			o.put("_status", "PREPAID");
			o.put("_status_id", "PREPAID");
		} else  if(o.getString("_delivery_type").contains("RETURNS") && o.getString("_status").contains("Picked Up")) {
			paymentType = o.getString("_delivery_type");
			o.put("_status", "Picked Up");
			o.put("_status_id", "RETURNS_PICKED_UP");
		}else {
			if(o.getString("_delivery_type").contains("COD") && o.getString("_status").contains("CASH_PAYMENT")) {
				paymentType = "Cash";
				o.put("_status_id", "CASH_PAYMENT");
			}else {
				paymentType = "Paid by POS";
				o.put("_status", "POS_PAID");
				o.put("_status_id", "POS_PAID");
			}
		}
		
		if(o.get("_relation").equals("Self")) {
			o.put("_receiver_name", o.getString("name"));
			o.put("_receiver_mobile", o.getString("mobile"));
		}
		data.put(EzeConstants.KEY_AMOUNT, Double.valueOf(o.getDouble("amount")));
		data.put(EzeConstants.KEY_PAYMENT_MODE, paymentType);
		
		intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, data.toString());
		
		intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_CREATE_SIGNATURE);
		
		intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
		String processCode = EzetapUIContext.getContext().get("loginresponse.defaultProcessCode").toString();
		if(processCode != null && !processCode.equals(""))		
			intent.putExtra(EzeConstants.KEY_PROCESS_CODE, processCode);
		intent.putExtra(EzeConstants.KEY_PROCESS_DATA_MAP, o.toString());
		
		intent.putExtra("isCachingEnabled", isCachingEnabled);

		callingActivity.startActivityForResult(intent, requestCode);
		
		} catch (Exception e) {
		e.printStackTrace();
		}

	}
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}
}
