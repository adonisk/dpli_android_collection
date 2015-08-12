package com.ezetap.android.api.caller.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.StringUtils;

public class LocalizationApiHelper extends ApiHelperBase implements ApiHelper {
	
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		if(!EzetapUtils.checkConnectivity(callingActivity)) return;
		if(o == null) return;
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
		intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_LOCALE);
		intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);
		
		intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
		o.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
		if(o.has(EzeConstants.KEY_LOCALE)) {
			String language = com.ezetap.utils.JUtils.getLanguage(callingActivity);
			if(language.equalsIgnoreCase(o.getString(EzeConstants.KEY_LOCALE))) {
				com.ezetap.android.utils.UIUtils.showToast("str_language_already_set", callingActivity);
				return;
			}
			intent.putExtra(EzeConstants.KEY_LOCALE, o.getString(EzeConstants.KEY_LOCALE));
		}
		intent.putExtra(EzeConstants.KEY_PROCESS_DATA_MAP, o.toString());
		callingActivity.startActivityForResult(intent, requestCode);		

		} catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
	}
	
	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		EzetapUIContext ctx = EzetapUIContext.getContext();
		if(ctx.get("locale") != null) {
			if(StringUtils.hasText((String)ctx.get("locale.locale"))) {
				ctx.put((String)ctx.get("loginresponse.locale"), (String)ctx.get("locale.locale"));
			}
		}
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
	}

}
