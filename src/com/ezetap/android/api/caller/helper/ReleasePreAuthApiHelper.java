package com.ezetap.android.api.caller.helper;

import org.json.JSONException;
import org.json.JSONObject;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

import android.app.Activity;
import android.content.Intent;

public class ReleasePreAuthApiHelper extends ApiHelperBase {

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		if(!EzetapUtils.checkConnectivity(callingActivity)) return;
		try {
		Intent intent = createIntent();
		JSONObject pd = new JSONObject();
		
		intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
		intent.addCategory(Intent.CATEGORY_DEFAULT);

		String targetAppPackage = ServiceAppUtils.checkAndDownloadServiceApp(callingActivity, requestCode);
		if (targetAppPackage == null) {
			return;
		}
		intent.setPackage(targetAppPackage);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_RELEASE_PRE_AUTH);
		intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);
		
		intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
		if(o.has("txnId")) {
			intent.putExtra(EzeConstants.KEY_TRANSACTION_ID, o.getString("txnId"));
			pd.put(EzeConstants.KEY_TRANSACTION_ID, o.getString("txnId"));
		}
		
		if(o.has(EzeConstants.KEY_ORDERID)) {
			intent.putExtra(EzeConstants.KEY_ORDERID, o.getString(EzeConstants.KEY_ORDERID));
			pd.put(EzeConstants.KEY_ORDERID, o.getString(EzeConstants.KEY_ORDERID));
		}
		
		if (!o.has(EzeConstants.KEY_AMOUNT)) {
			com.ezetap.android.utils.UIUtils.showToast("incorrect_amount", callingActivity);
			return;
		}
		intent.putExtra(EzeConstants.KEY_AMOUNT, new Double(o.getDouble("amount") ));
		if(o.has(EzetapConstDef.ADDITIONAL_AMOUNT)) {
			double tip = o.getDouble(EzetapConstDef.ADDITIONAL_AMOUNT);
			
			if (tip > 0.0) {
				intent.putExtra(EzeConstants.KEY_TIP_AMOUNT, new Double(tip));
			}
		}
		intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
		intent.putExtra("isCachingEnabled", isCachingEnabled);
		
		callingActivity.startActivityForResult(intent, requestCode);		

		} catch(JSONException e){
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
