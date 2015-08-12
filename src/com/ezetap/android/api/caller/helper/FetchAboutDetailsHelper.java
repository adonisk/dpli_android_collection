package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ezetap.android.api.caller.helper.service.EzetapServiceBaseConstants;
import com.ezetap.android.api.caller.http.HttpClientUtils;
import com.ezetap.android.api.caller.http.HttpResponseHandler;
import com.ezetap.utils.EzeConstants;

public class FetchAboutDetailsHelper extends Activity{

	private static final String DEBUG_TAG = "FetchAboutDetailsHelper";
	private static final String UNSUPPORTED_API = "UNSUPPORTED_API";
	private static final String UNSUPPORTED_API_MSG = "Unsupported API called";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent =  getIntent();

		String apiName = intent.getStringExtra(EzeConstants.KEY_ACTION);

		if(apiName.equals("fetchAbout")){
			ExternalServiceFetcher f = new EzetapAboutDetailFetcher(intent, this);
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
	private class EzetapAboutDetailFetcher implements ExternalServiceFetcher{
		private Activity _context;

		public EzetapAboutDetailFetcher(Intent intent, Activity context){
			this._context = context;
		}

		@Override
		public void fetch(){
			String language = com.ezetap.utils.JUtils.getLanguage(_context);
			String url = "http://d.eze.cc/support_" + language + ".json";
			HttpClientUtils catalogCall = new HttpClientUtils();
			catalogCall.process(EzetapServiceBaseConstants.VAL_POST, url, new JSONObject(), getIntent().getIntExtra("requestCode", -1), new EzetapExternalServiceResponseHandler(_context));			
		}
	}

	private class EzetapExternalServiceResponseHandler implements HttpResponseHandler {

		Activity _context;
		private EzetapExternalServiceResponseHandler(Activity context) {
			_context = context;
		}
		@Override
		public void handleResponse(JSONObject response, int requestCode) {
			Intent intent = new Intent();
			if(response!=null && response.has("success")) {
				intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, response.toString());
			} 
			setResult(EzeConstants.RESULT_SUCCESS, intent);
			finish();
		}

		@Override
		public void handleError(Exception e, String msg, int requestCode) {
			// Set result to success as we do not want application to fail 
			// logging in if the fetch about details fail.
			setResult(EzeConstants.RESULT_SUCCESS);
			finish();
		}
	}
}