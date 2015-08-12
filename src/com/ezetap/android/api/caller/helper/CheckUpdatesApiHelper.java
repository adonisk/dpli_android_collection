package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

public class CheckUpdatesApiHelper extends ApiHelperBase implements ApiHelper {
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {

	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
//		if(!EzetapUtils.checkConnectivity(callingActivity)) return;
//		Intent intent = new Intent(callingActivity, DummyUpdate.class);
//		callingActivity.startActivityForResult(intent, requestCode);
		
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
			
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_CHECK_UPDATES);

			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			
			callingActivity.startActivityForResult(intent, requestCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		//EzetapUpdateApiHelper.update(o, callingActivity, reqCode);
		//EzetapUpdateApiHelper.update(result, callingActivity,reqCode);
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {

	}

}
