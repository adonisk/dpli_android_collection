package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

public class ChangePasswordApiHelper extends ApiHelperBase implements ApiHelper {

	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		if(!EzetapUtils.checkConnectivity(callingActivity)) return;
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
			
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_CHANGE_PWD);
			if(o != null && o.has("currentPassword") && o.has("newPassword")){
				String oldPassword = o.get("currentPassword").toString();
				String newPassword = o.get("newPassword").toString();
				String newPasswordConf = o.getString("confirmNewPassword").toString();
				
				if(!newPassword.equals(newPasswordConf)) {
					com.ezetap.android.utils.UIUtils.showToast("new_pwd_mismatch", callingActivity);
					return;
				}
				
				intent.putExtra(EzeConstants.KEY_PASSWORD, oldPassword);
				intent.putExtra(EzeConstants.KEY_NEW_PASSWORD, newPassword);
			}else
				intent.putExtra(EzeConstants.KEY_USING_CUSTOM_UI, false);
			
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			
			callingActivity.startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		callingActivity.deleteFile(EzetapConstDef.FILENAME);
		EzetapUIContext.getContext().clear();
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}

}
