/**
 * 
 */
package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import com.ezetap.utils.IntentIntegrator;

import android.app.Activity;

/**
 * @author vivek
 *
 */
public class ScanBarcodeApiHelper extends ApiHelperBase implements ApiHelper {

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
		IntentIntegrator integrator = new IntentIntegrator(callingActivity, requestCode);
		integrator.initiateScan();

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
