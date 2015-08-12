package com.ezetap.android.api.caller.helper;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ezetap.android.api.caller.helper.service.EzetapServiceBaseConstants;
import com.ezetap.android.api.caller.http.HttpClientUtils;
import com.ezetap.android.api.caller.http.HttpResponseHandler;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.utils.EzeConstants;

public class FeedbackHelperActivity extends Activity{
	
	private static final String DEBUG_TAG = "FeedbackHelperActivity";
	private static final String UNSUPPORTED_API = "UNSUPPORTED_API";
	private static final String UNSUPPORTED_API_MSG = "Unsupported API called";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent =  getIntent();

		String apiName = intent.getStringExtra(EzeConstants.KEY_ACTION);
		
		if(apiName.equals("feedback")){
			ExternalServiceFetcher f = new EzetapFeedbackFetcher(intent, this);
			f.fetch();
		} 
		else {
			Intent responseIntent = new Intent();
			responseIntent.putExtra(EzeConstants.KEY_ERROR_CODE, UNSUPPORTED_API);
			responseIntent.putExtra(EzeConstants.KEY_ERROR_MESSAGE, UNSUPPORTED_API_MSG);
			setResult(EzeConstants.RESULT_FAILED, responseIntent);
			finish();
		}
	}
	
		
	/**
	 * Private interface to be implemented by External Data Fetchers
	 * @author vivek
	 *
	 */
	private interface ExternalServiceFetcher {
		public void fetch();
	}
	
	/**
	 * Private inner class to fetch about details from external service
	 * @author deepak
	 *
	 */
	private class EzetapFeedbackFetcher implements ExternalServiceFetcher{
		private Activity _context;
		
		public EzetapFeedbackFetcher(Intent intent, Activity context){
			this._context = context;
		}
		
		@Override
		public void fetch(){
//			String orgCode = (String)EzetapUIContext.getContext().get("loginresponse.orgCode");
//			String url = "http://d.eze.cc/feedback/".concat("merchant_").concat(orgCode.toLowerCase()).concat("_en.json");
			String url = "http://d.eze.cc/feedback/merchant_feedback.json";
			HttpClientUtils catalogCall = new HttpClientUtils();
			catalogCall.process(EzetapServiceBaseConstants.VAL_POST, url, new JSONObject(), getIntent().getIntExtra("requestCode", -1), new EzetapExternalServiceResponseHandler(_context));			
			EzetapUtils.setFeedBackCount(0);
		}
	}
	
	private class EzetapExternalServiceResponseHandler implements HttpResponseHandler {

		Activity _context;
		private EzetapExternalServiceResponseHandler(Activity context) {
			_context = context;
		}
		@Override
		public void handleResponse(JSONObject response, int requestCode) {
			try {
				if(response!=null && response.has("success")) {
					if(response.getBoolean("success")) {
						Intent intent = new Intent();
						intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, response.toString());
						setResult(EzeConstants.RESULT_SUCCESS, intent);
						finish();
					}
					else {
						Intent intent = new Intent();
						intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, response.toString());
						setResult(EzeConstants.RESULT_FAILED, intent);
						finish();
					}
				} 
			} catch (JSONException e) {
				Log.v(DEBUG_TAG, "error reading JSON response from external service. delegating to handleError.", e);
				handleError(e, "error reading JSON response from external service", requestCode);
			}
		}

		@Override
		public void handleError(Exception e, String msg, int requestCode) {
			Intent intent = new Intent();
			JSONObject errorResponse = new JSONObject();
			try {
				errorResponse.put("success", false);
				errorResponse.put("errorCode", "RETRIEVE_DATA_FAILED");
				errorResponse.put("errorMessage", msg);
				intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, errorResponse.toString());
				setResult(EzeConstants.RESULT_FAILED, intent);
				finish();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}
}