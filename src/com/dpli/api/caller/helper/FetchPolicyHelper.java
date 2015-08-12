package com.dpli.api.caller.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ezetap.android.api.caller.helper.service.EzetapServiceBaseConstants;
import com.ezetap.android.api.caller.http.EzetapURL;
import com.ezetap.android.api.caller.http.HttpClientUtils;
import com.ezetap.android.api.caller.http.HttpResponseHandler;
import com.ezetap.android.app.properties.EzetapProperties;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.utils.EzeConstants;

public class FetchPolicyHelper extends Activity{
	
	private static final String DEBUG_TAG = "FetchPolicyHelper";
	private static final String UNSUPPORTED_API = "UNSUPPORTED_API";
	private static final String UNSUPPORTED_API_MSG = "Unsupported API called";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent =  getIntent();

		String apiName = intent.getStringExtra(EzeConstants.KEY_ACTION);
		
		if(apiName.equals("fetchPolicy"))
		{
			ExternalServiceFetcher f = new DHFLPolicyDetailFetcher(intent, this);
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
	 * Private inner class to fetch customer details from external service
	 * @author karthik
	 *
	 */
	private class DHFLPolicyDetailFetcher implements ExternalServiceFetcher{
		private Intent _intent;
		private Activity _context;
		private static final String apiName = "fetchDetails";
		
		public DHFLPolicyDetailFetcher(Intent intent, Activity context){
			this._intent = intent;
			this._context = context;
		}
		
		@Override
		public void fetch() {
			JSONObject data = null;
			JSONObject jsonObject = new JSONObject();
			if(_intent.hasExtra(EzeConstants.KEY_JSON_REQ_DATA))
				try {
					data = new JSONObject(_intent.getStringExtra(EzeConstants.KEY_JSON_REQ_DATA));
					if(data.has("policyNo") && (data.getString("policyNo").length() > 0))
						jsonObject.put("policy_no", data.getString("policyNo"));
					else
					{
						Intent responseIntent = new Intent();
						JSONObject response = new JSONObject();
						response.put(EzeConstants.KEY_ERROR_CODE, "INVALID_DATA");
						response.put(EzeConstants.KEY_ERROR_MESSAGE, "Please enter policy no");
						responseIntent.putExtra(EzeConstants.KEY_RESPONSE_DATA, response.toString());
						setResult(EzeConstants.RESULT_FAILED, responseIntent);
						finish();
						return;
//						jsonObject.put("policy_no", "");
					}

					//					if(data.has("customerName"))
//						jsonObject.put("customerName", data.getString("customerName"));
//					else
//						jsonObject.put("customerName", "");
//					JSONObject customerData = new JSONObject();
//					policyData.put(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
//					jsonObject.put("customerData", customerData);

					jsonObject.put("user_name", EzetapUIContext.getContext().getUserName());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			
			//String sessionKey = (String) EzetapUIContext.getContext().get("loginresponse.sessionKey");
			HttpClientUtils httpClient = new HttpClientUtils();
			//httpClient.setSessionKey(sessionKey);
			httpClient.process(EzetapServiceBaseConstants.VAL_POST, "http://103.3.60.118:3000/pramerica/policy_details", jsonObject, getIntent().getIntExtra("requestCode", -1), new DHFLPolicyDetailResponseHandler());
		}
	}
	
	/**
	 * Private inner class to handle response from external service
	 * @author vivek
	 *
	 */
	
	private class DHFLPolicyDetailResponseHandler implements HttpResponseHandler {

		@Override
		public void handleResponse(JSONObject response, int requestCode) {
			try {
				if(response!=null && response.has("success")) {
					if(response.getBoolean("success")) {
						Intent intent = new Intent();
						intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, response.toString());
						setResult(EzeConstants.RESULT_SUCCESS, intent);
						finish();
						
					} else {
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
				errorResponse.put("errorMessage", "Failed to retrieve data from External Service");
				intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, errorResponse.toString());
				setResult(EzeConstants.RESULT_FAILED, intent);
				finish();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
