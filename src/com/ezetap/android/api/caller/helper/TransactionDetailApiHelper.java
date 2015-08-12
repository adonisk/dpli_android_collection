package com.ezetap.android.api.caller.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.utils.EzeConstants;

public class TransactionDetailApiHelper extends ApiHelperBase implements ApiHelper {
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {

	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		Intent intent = new Intent(callingActivity, TransactionDetailActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		intent.putExtra(EzeConstants.KEY_ACTION, "txnDetail");
		
		if(o!=null) intent.putExtra("TXN_JSON", o.toString());
		
		callingActivity.startActivityForResult(intent, requestCode);
	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
	}

}
