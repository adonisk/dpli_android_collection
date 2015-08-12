/**
 * 
 */
package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.utils.EzeConstants;

/**
 * @author deepak
 *
 */
public class FetchAboutApiHelper extends ApiHelperBase {
	

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
		Intent intent = new Intent(callingActivity, FetchAboutDetailsHelper.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtra(EzeConstants.KEY_ACTION, "fetchAbout");
		intent.putExtra("requestCode", requestCode);
		if(o != null)
			intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, o.toString());
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
