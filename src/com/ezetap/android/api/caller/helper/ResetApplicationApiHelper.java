package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.JUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResetApplicationApiHelper extends ApiHelperBase implements ApiHelper {

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		
	}

	@Override
	public void callApi(JSONObject o, final Activity callingActivity, final int requestCode) {
		try {
			final String targetAppPackage = ServiceAppUtils.checkAndDownloadServiceApp(callingActivity, requestCode);
			if (targetAppPackage == null) {
				return;
			}
			final Dialog dialog = new Dialog(callingActivity);
		    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    dialog.setCancelable(false);
			dialog.setContentView(callingActivity.getResources().getIdentifier("custom_alert_dialog", "layout", callingActivity.getPackageName()));
			// set the title
			TextView title = (TextView) dialog.findViewById(callingActivity.getResources().getIdentifier("dialogTitle", "id", callingActivity.getPackageName()));
			title.setText("Reset");
			// set the custom dialog components - text, image and button
			TextView text = (TextView) dialog.findViewById(callingActivity.getResources().getIdentifier("dialogText", "id", callingActivity.getPackageName()));
			text.setText("Resets Ezetap Service Application and if you are logged in, you will be asked to login again once done. Do you wish to continue?");
			// if left button is clicked
			Button leftButton = (Button) dialog.findViewById(callingActivity.getResources().getIdentifier("leftButton", "id", callingActivity.getPackageName()));
			leftButton.setText("No");
			leftButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			// if right button is clicked
			Button rightButton = (Button) dialog.findViewById(callingActivity.getResources().getIdentifier("rightButton", "id", callingActivity.getPackageName()));
			rightButton.setText("Yes");
			rightButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = createIntent();

					intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setPackage(targetAppPackage);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
					intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_RESET_APPLICATION);
					intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
					intent.putExtra("isCachingEnabled", isCachingEnabled);
					
					callingActivity.startActivityForResult(intent, requestCode);
					dialog.dismiss();
				}
			});
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
		
	}

}
