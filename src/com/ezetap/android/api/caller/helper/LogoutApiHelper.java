package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

import android.app.Activity;
import android.content.Intent;

public class LogoutApiHelper extends ApiHelperBase implements ApiHelper {

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		callingActivity.deleteFile(EzetapConstDef.FILENAME);
		EzetapUIContext.getContext().clear();
	}

	@Override
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
			
			
			JSONObject reqData = new JSONObject();
			
			reqData.put(EzeConstants.KEY_ACTION, EzeConstants.ACTION_LOGOUT);
			reqData.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			
			intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, reqData.toString());
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			
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
