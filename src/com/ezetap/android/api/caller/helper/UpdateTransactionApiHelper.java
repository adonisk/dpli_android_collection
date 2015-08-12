package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

import android.app.Activity;
import android.content.Intent;

public class UpdateTransactionApiHelper extends ApiHelperBase{

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		if(!EzetapUtils.checkConnectivity(callingActivity)) return;
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
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
			
			String txnID = o.getString(EzeConstants.KEY_TRANSACTION_ID);
			JSONObject reqData = new JSONObject();
			reqData.put(EzeConstants.KEY_ACTION, EzeConstants.ACTION_UPDATE_TXN);
			
			reqData.put(EzeConstants.KEY_TRANSACTION_ID, txnID);
			String mobile = new String();
			String emailID = new String();
			if(o.has(EzeConstants.KEY_CUSTOMER_MOBILE)) {
				mobile = o.getString(EzeConstants.KEY_CUSTOMER_MOBILE);
				if(mobile == null) mobile = new String();
				if(mobile.length() > 0)
					reqData.put(EzeConstants.KEY_CUSTOMER_MOBILE, mobile);
			}
			if(o.has(EzeConstants.KEY_CUSTOMER_EMAIL)) {
				emailID = o.getString(EzeConstants.KEY_CUSTOMER_EMAIL);
				if(emailID == null) emailID = new String();
				if(emailID.length() > 0) {
					if(com.ezetap.utils.JUtils.isEmailValid(emailID))
						reqData.put(EzeConstants.KEY_CUSTOMER_EMAIL, emailID);
					else {
						com.ezetap.android.utils.UIUtils.showToast("msg_invalid_email", callingActivity);
						return;
					}
				}
			}
			if(mobile.length() == 0 && emailID.length() == 0) {
				com.ezetap.android.utils.UIUtils.showToast("msg_enter_mob_email", callingActivity);
				return;
			}
			reqData.put(EzeConstants.KEY_NOTIFY_CUSTOMER, true);
			reqData.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			reqData.put(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);
			
			intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, reqData.toString());
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			callingActivity.startActivityForResult(intent, requestCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
