package com.ezetap.android.api.caller.helper;

import com.ezetap.utils.EzeConstants;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.ServiceAppUtils;
public class FetchDatabaseApiHelper extends ApiHelperBase implements ApiHelper {

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
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_ORDER_LIST);
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
			
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());

			intent.putExtra("isCachingEnabled", isCachingEnabled);
					
			String processCode = EzetapUIContext.getContext().get("loginresponse.defaultProcessCode").toString();
			if(processCode != null && !processCode.equals(""))
				intent.putExtra(EzeConstants.KEY_PROCESS_CODE, processCode);
			if(o.has(EzeConstants.KEY_SEARCH_PARAM)) {
				intent.putExtra(EzeConstants.KEY_SEARCH_PARAM, o.getString(EzeConstants.KEY_SEARCH_PARAM));
			}
			else if(processCode.equalsIgnoreCase("SALES")) {
				intent.putExtra(EzeConstants.KEY_SEARCH_PARAM, "SALES");
			}			
			
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			callingActivity.startActivityForResult(intent, requestCode);		
		} catch(Exception e) {}
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