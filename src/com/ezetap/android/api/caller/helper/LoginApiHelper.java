package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.ezetap.android.app.properties.EzetapProperties;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.JUtils;

public class LoginApiHelper extends ApiHelperBase implements ApiHelper {

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		EzetapProperties.clear();
		callingActivity.deleteFile(EzetapConstDef.FILENAME);
	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		if(!EzetapUtils.checkConnectivity(callingActivity)) return;
		try {
			Intent intent = createIntent();

			EzetapUIContext.getContext().setUserName(o.getString(EzetapConstDef.USERNAME));
			//EzetapUIContext.getContext().setPasswd(o.getString(EzetapConstDef.PASSWORD));
			
			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = ServiceAppUtils.checkAndDownloadServiceApp(callingActivity, requestCode);
			if (targetAppPackage == null) {
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			String p = o.getString(EzetapConstDef.PASSWORD);
			String u = o.getString(EzetapConstDef.USERNAME);
			
			EzetapUIContext.getContext().removeJSON("logininput");
			EzetapUIContext.getContext().removeJSON("loginoutput");
			
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_LOGIN);

			intent.putExtra(EzeConstants.KEY_PASSWORD, p);
			intent.putExtra(EzeConstants.KEY_USERNAME, u);
			
			
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			
			callingActivity.startActivityForResult(intent, requestCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
		View username = callingActivity.findViewById(callingActivity.getResources().getIdentifier("txt_username", "id", callingActivity.getPackageName()));
		if(username != null){
			((EditText)username).setText("");
		}
		View password = callingActivity.findViewById(callingActivity.getResources().getIdentifier("txt_password", "id", callingActivity.getPackageName()));
		if(password != null){
			((EditText)password).setText("");
		}
	}

}
