package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

public class VoidApiHelper extends ApiHelperBase implements ApiHelper {

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {

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
			
			reqData.put(EzeConstants.KEY_ACTION, EzeConstants.ACTION_VOID);

			reqData.put(EzeConstants.KEY_TRANSACTION_ID, txnID);
			reqData.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			reqData.put(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);
			
			intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, reqData.toString());
			
			
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_VOID);

			
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			
			callingActivity.startActivityForResult(intent, requestCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {

	}

}
