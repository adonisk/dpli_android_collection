package com.ezetap.android.api.caller.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

public class OrderListApiHelper extends ApiHelperBase implements ApiHelper {

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
			
			intent.putExtra("IS_FULL_REFRESH", true);
			intent.putExtra("isCachingEnabled", isCachingEnabled);
					
			String processCode = EzetapUIContext.getContext().get("loginresponse.defaultProcessCode").toString();
			if(processCode != null && !processCode.equals(""))		
				intent.putExtra(EzeConstants.KEY_PROCESS_CODE, processCode);
			
			if(processCode.equalsIgnoreCase("SALES")) {
				try {
					JSONObject oo = new JSONObject();
					JSONArray status = new JSONArray();
					status.put("PAYMENT_PENDING");
					status.put("PAYMENT_FAILED");
					oo.put("status", status);
					intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, oo.toString());
				} catch (JSONException e) {}
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
		EzetapUtils.setLastSyncedTime(callingActivity);
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
	}

}