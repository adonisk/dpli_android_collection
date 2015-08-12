package com.ezetap.android.api.caller.helper;

import org.json.JSONException;
import org.json.JSONObject;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.utils.EzeConstants;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import android.app.Activity;
import android.content.Intent;

public class LoyaltyCardBurnApiHelper extends ApiHelperBase {

	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {

	}

	@Override
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
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_LOYALTY);
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);
			
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			if (o.has(EzeConstants.KEY_AMOUNT) && o.getString(EzeConstants.KEY_AMOUNT).length() != 0) {
				intent.putExtra(EzeConstants.KEY_AMOUNT, new Double(com.ezetap.android.utils.UIUtils.unFormat(o.getString(EzeConstants.KEY_AMOUNT))));
			}

			if (o.has(EzeConstants.KEY_CUSTOMER_MOBILE) && o.getString(EzeConstants.KEY_CUSTOMER_MOBILE).length() != 0) {
				intent.putExtra(EzeConstants.KEY_CUSTOMER_MOBILE, o.getString(EzeConstants.KEY_CUSTOMER_MOBILE));
			}
			
			intent.putExtra(EzeConstants.KEY_TRANSACTION_TYPE, EzeConstants.TXN_TYPE_BURN_LOYALTY);
			intent.putExtra(EzeConstants.KEY_LOYALTY_TYPE, EzeConstants.TYPE_LOYALTY_CARD);
			
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