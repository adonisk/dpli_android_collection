package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.ezetap.android.utils.EzetapUtils;

public class CallNumberApiHelper extends ApiHelperBase implements
ApiHelper{
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		try {
			String mobile = new String();
			if(o.has("mobile")) {
				mobile = o.get("mobile").toString();
			}
			else if(o.has("phone")) {
				mobile = o.get("phone").toString();
			} else {
				com.ezetap.android.utils.UIUtils.showToast("str_incorrect_no", callingActivity);
				return;
			}
			String uri = "tel:" + EzetapUtils.getCountryCode() + mobile;
			if(mobile.startsWith("0"))
				uri = "tel:" + mobile;
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse(uri));
			if(callingActivity != null)
				callingActivity.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		//EzetapUIContext.getContext().put("aboutus.mobile", "08049114999");
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
