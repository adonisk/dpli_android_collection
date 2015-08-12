package com.dpli.api.caller.helper;

import org.json.JSONObject;
import org.json.JSONException;

import com.ezetap.utils.EzeConstants;
import com.ezetap.android.api.caller.helper.ApiHelperBase;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class FetchPolicyApiHelper extends ApiHelperBase {

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {

	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		Intent intent = new Intent(callingActivity, FetchPolicyHelper.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtra(EzeConstants.KEY_ACTION, "fetchPolicy");
		intent.putExtra("requestCode", requestCode);

		if(o != null)
		{
			intent.putExtra(EzeConstants.KEY_JSON_REQ_DATA, o.toString());
		}
		
		callingActivity.startActivityForResult(intent, requestCode);
	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {

	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {

	}

}
