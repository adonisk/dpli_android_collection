package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;

public interface ApiHelper {

	public void preApiCall(JSONObject o, Activity callingActivity);
	public void callApi(JSONObject o, Activity callingActivity, int requestCode);
	public void postApiCall(JSONObject result, Activity callingActivity);
	public void onApiError(JSONObject result, Activity callingActivity);
}
