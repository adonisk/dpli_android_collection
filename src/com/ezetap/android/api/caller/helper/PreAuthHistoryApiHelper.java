package com.ezetap.android.api.caller.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

import android.app.Activity;
import android.content.Intent;

public class PreAuthHistoryApiHelper extends ApiHelperBase implements
		ApiHelper {

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
			
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_PREAUTH_HISTORY);
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN,false);
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			
			if(o!=null && o.has(EzeConstants.KEY_START_DATE))
				intent.putExtra(EzeConstants.KEY_START_DATE, o.getString(EzeConstants.KEY_START_DATE));
			else {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -1);
				String startDate = dateFormat.format(cal.getTime());
				intent.putExtra(EzeConstants.KEY_START_DATE, startDate);
			}
			if(o!=null && o.has(EzeConstants.KEY_END_DATE))
				intent.putExtra(EzeConstants.KEY_END_DATE, o.getString(EzeConstants.KEY_END_DATE));
			else {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				Calendar cal = Calendar.getInstance();
				String endDate = dateFormat.format(cal.getTime());
				intent.putExtra(EzeConstants.KEY_END_DATE, endDate);
			}

			intent.putExtra(EzetapConstDef.DBG_HOST, ApiHelperBase.DBG_HOST_IP);
			
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			
			callingActivity.startActivityForResult(intent, requestCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
		if(o!=null && o.has(EzeConstants.KEY_START_DATE)) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
				SimpleDateFormat expectedFormat = new SimpleDateFormat("dd MMM yy", java.util.Locale.ENGLISH);
				String date = o.getString(EzeConstants.KEY_START_DATE);
				String returnDate = date;
				dateFormat.setLenient(true);
				try {
					returnDate = expectedFormat.format(dateFormat.parse(date));
					
				} catch (ParseException e) {
				}
				if (returnDate == null || returnDate.length() == 0) {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", java.util.Locale.ENGLISH);

					dateFormat.setLenient(true);
					try {
						returnDate = expectedFormat.format(dateFormat.parse(date));

					} catch (ParseException e) {
					}
				}
				EzetapUIContext.getContext().put("txnSummary.date", returnDate);
			} catch (JSONException e) {
			}
		}
		else {
//			EzetapUIContext.getContext().put("txnSummary.date", new SimpleDateFormat("dd MMM yy", Locale.US).format(GregorianCalendar.getInstance().getTime()));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			Calendar cal = Calendar.getInstance();
			String endDate = dateFormat.format(cal.getTime());
			cal.add(Calendar.MONTH, -1);
			String startDate = dateFormat.format(cal.getTime());
			EzetapUIContext.getContext().put("txnSummary."+EzeConstants.KEY_START_DATE, startDate);
			EzetapUIContext.getContext().put("txnSummary."+EzeConstants.KEY_END_DATE, endDate);
		}
	}
	
	@Override
	public void postApiCall(JSONObject o, Activity callingActivity) {
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
		// TODO Auto-generated method stub
		
	}

}
