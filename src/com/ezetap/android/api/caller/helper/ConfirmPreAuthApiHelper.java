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

public class ConfirmPreAuthApiHelper extends ApiHelperBase implements ApiHelper {

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
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_CONFIRM_PRE_AUTH);
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
			
			JSONObject pd = new JSONObject();
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			intent.putExtra(EzeConstants.KEY_AMOUNT, new Double(o.getDouble("amount")));
			pd.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			pd.put(EzeConstants.KEY_AMOUNT, new Double(o.getDouble("amount")));		
			if(o.has("txnId")) {
				intent.putExtra(EzeConstants.KEY_TRANSACTION_ID, o.getString("txnId"));
				pd.put(EzeConstants.KEY_TRANSACTION_ID, o.getString("txnId"));
			}
			if(o.has("__ref")) {
				intent.putExtra(EzeConstants.KEY_ORDERID, o.getString("__ref"));
				pd.put(EzeConstants.KEY_ORDERID, o.getString("__ref"));
			}
			if(o.has("mobile")) {
				intent.putExtra(EzeConstants.KEY_CUSTOMER_MOBILE, o.getString("mobile"));
				pd.put(EzeConstants.KEY_CUSTOMER_MOBILE, o.getString("mobile"));
			}
			if(o.has(EzeConstants.KEY_EXTERNAL_REF_NUMBER2)) {
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER2, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER2));
				pd.put(EzeConstants.KEY_EXTERNAL_REF_NUMBER2, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER2));
			}
			if(o.has(EzeConstants.KEY_EXTERNAL_REF_NUMBER3)) {
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER3, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER3));
				pd.put(EzeConstants.KEY_EXTERNAL_REF_NUMBER3, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER3));
			}
			JSONObject saveData = new JSONObject();
			saveData.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			saveData.put(EzeConstants.KEY_PROCESS_DATA_MAP, pd.toString());

			callingActivity.startActivityForResult(intent, requestCode);		
		} catch(JSONException e){
		}
	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
	}
}
