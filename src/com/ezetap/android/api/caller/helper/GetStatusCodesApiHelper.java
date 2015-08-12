/**
 * 
 */
package com.ezetap.android.api.caller.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

/**
 * @author vivek
 *
 */
public class GetStatusCodesApiHelper extends ApiHelperBase implements ApiHelper {

	/* (non-Javadoc)
	 * @see com.ezetap.android.api.caller.helper.ApiHelperBase#preApiCall(org.json.JSONObject, android.app.Activity)
	 */
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ezetap.android.api.caller.helper.ApiHelperBase#callApi(org.json.JSONObject, android.app.Activity, int)
	 */
	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
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
		intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_GET_STATUS_CODES);
		intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
		
		try {
			String processCode = EzetapUIContext.getContext().get("loginresponse.defaultProcessCode").toString();
			JSONObject oo = new JSONObject();
			oo.put("username", EzetapUIContext.getContext().getUserName());
			oo.put("processCode", processCode);
			intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, oo.toString());
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		intent.putExtra("isCachingEnabled", isCachingEnabled);
				
		intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
		callingActivity.startActivityForResult(intent, requestCode);
	}

	/* (non-Javadoc)
	 * @see com.ezetap.android.api.caller.helper.ApiHelperBase#postApiCall(org.json.JSONObject, android.app.Activity)
	 */
	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ezetap.android.api.caller.helper.ApiHelperBase#onApiError(org.json.JSONObject, android.app.Activity)
	 */
	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
		// TODO Auto-generated method stub

	}

}
