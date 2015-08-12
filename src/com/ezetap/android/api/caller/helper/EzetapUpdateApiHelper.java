package com.ezetap.android.api.caller.helper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ezetap.utils.EzeConstants;

import android.app.Activity;
import android.content.Intent;

public class EzetapUpdateApiHelper extends ApiHelperBase implements ApiHelper{
	
	@SuppressWarnings("finally")
	public static void update(JSONObject o, Activity a, int requestCode){
		if(o == null || !o.has(EzeConstants.KEY_SUCCESS)) {
			//checkUpdates failed. Proceed as normal.
			processUpdate(new ArrayList<String>(), a, requestCode);
		}
		
		try {
			ArrayList<String> l = new ArrayList<String>();
			if(o.getBoolean(EzeConstants.KEY_SUCCESS)) {
				if(o.has(EzeConstants.KEY_APP)) {
					JSONArray app = o.getJSONArray(EzeConstants.KEY_APP);
					for(int i=0; i<app.length(); i++) {
						JSONObject appObj = app.getJSONObject(i);
						if(appObj.has(EzeConstants.KEY_HAS_UPGRADE) && appObj.getBoolean(EzeConstants.KEY_HAS_UPGRADE)) {
							if(appObj.getString(EzeConstants.KEY_APPLICATION_ID).equals(EzeConstants.KEY_SERVICE_APP_ID)) {
								//special handling for service app to ensure it is the first one to be updated.
								l.add(0, appObj.toString());
							} else {
								l.add(appObj.toString());
							}
						}
					}
				}
				processUpdate(l, a, requestCode);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			return;
		}
	}
	
	private static void processUpdate(ArrayList<String> list, Activity context, int requestCode) {
		Intent intent = new Intent(context, EzetapUpdateActivity.class);
		intent.putExtra(EzeConstants.KEY_UPDATE_JSON, list);
		context.startActivityForResult(intent, requestCode);
	}

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		update(o, callingActivity, requestCode);
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
