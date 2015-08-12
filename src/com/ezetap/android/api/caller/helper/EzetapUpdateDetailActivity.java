package com.ezetap.android.api.caller.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.EzetapDownloadUtils;

public class EzetapUpdateDetailActivity extends Activity implements Handler.Callback{

	private JSONObject json = null;
	BroadcastReceiver packageListener = null;
	boolean installed = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		packageListener =  new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				installed =  true;
			}
		};
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
	    intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
	    intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
	    intentFilter.addDataScheme("package");
	    registerReceiver(packageListener, intentFilter);
		try {		
			Intent intent = getIntent();
			String json = intent.getStringExtra(EzeConstants.KEY_UPDATE_JSON);
			this.json = new JSONObject(json);
			this.setContentView(getResources().getIdentifier("update_details", "layout", this.getPackageName()));
			populateUI();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void populateUI(){
		try {
			String applicationId = "";
			String displayVersion = "";
			String info = "No additional information available";
			
			if(json.has(EzeConstants.KEY_APPLICATION_ID))
				applicationId = json.getString(EzeConstants.KEY_APPLICATION_ID);
			
			if(json.has(EzeConstants.KEY_DISPLAY_VERSION))
				displayVersion = json.getString(EzeConstants.KEY_DISPLAY_VERSION);
			
			if(json.has(EzeConstants.KEY_UPGRADE_NOTES))
				info = json.getString(EzeConstants.KEY_UPGRADE_NOTES);
			
			String downloadURL = json.getString(EzeConstants.KEY_UPDATE_URL);
			
			if(!json.has(EzeConstants.KEY_UPDATE_SEVERITY) && 
					(!(json.getString(EzeConstants.KEY_UPDATE_SEVERITY).equals(EzeConstants.VALUE_SEVERITY_MANDATORY)) || 
					!(json.getString(EzeConstants.KEY_UPDATE_SEVERITY).equals(EzeConstants.VALUE_SEVERITY_OPTIONAL)))) {
				com.ezetap.android.utils.UIUtils.showToastMessage("Invalid update severity data", EzetapUpdateDetailActivity.this);
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
			
			String severity = json.getString(EzeConstants.KEY_UPDATE_SEVERITY);
			
			if(severity.equals(EzeConstants.VALUE_SEVERITY_MANDATORY)) {
				((Button) findViewById(this.getResources().getIdentifier("button2", "id", this.getPackageName()))).setEnabled(false);
			}
			
			String topBarString = "";
			
			if(applicationId.equals(EzeConstants.KEY_SERVICE_APP_ID)) {
				topBarString = com.ezetap.android.utils.UIUtils.getResourceString("new_sdk_available", EzetapUpdateDetailActivity.this);
				//"A new version of Ezetap SDK is available";
			} else {
				topBarString = com.ezetap.android.utils.UIUtils.getResourceString("new_app_available", EzetapUpdateDetailActivity.this) + applicationId;
//				"A new version of your application is available - " + applicationId;
			}
			
			if(severity.equals(EzeConstants.VALUE_SEVERITY_MANDATORY)) {
				topBarString = topBarString.concat(". This update is MANDATORY");
			}
			
 			((TextView) findViewById(this.getResources().getIdentifier("new_version_available", "id", this.getPackageName()))).setText(topBarString);
			((TextView) findViewById(this.getResources().getIdentifier("version_info", "id", this.getPackageName()))).setText("Version : " +displayVersion);
			((TextView) findViewById(this.getResources().getIdentifier("update_info", "id", this.getPackageName()))).setText(info);
				
			
		} catch (JSONException e) {
			com.ezetap.android.utils.UIUtils.showToastMessage("Invalid update data", EzetapUpdateDetailActivity.this);
			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
		}
		
	}
	
    public void okClicked(View v){
    	try {
    		String fileName = json.getString(EzeConstants.KEY_APPLICATION_ID);
    		String downloadURL = json.getString(EzeConstants.KEY_UPDATE_URL);
			EzetapDownloadUtils utils = new EzetapDownloadUtils(downloadURL, this, fileName, this );
			utils.start();
    	} catch(JSONException e) {
    		e.printStackTrace();
    	}
    }
    
    public void cancelClicked(View v){
    	String severity = EzeConstants.VALUE_SEVERITY_MANDATORY;
		try {
			severity = json.getString(EzeConstants.KEY_UPDATE_SEVERITY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Intent intent = new Intent();
    	if(severity.equals(EzeConstants.VALUE_SEVERITY_MANDATORY)) {
    		setResult(RESULT_CANCELED, intent);
    	} else {
    		setResult(RESULT_OK, intent);
    	}
		finish();
    }

	@Override
	public boolean handleMessage(Message msg) {
		com.ezetap.android.utils.UIUtils.showToast("update_dwnlded", EzetapUpdateDetailActivity.this);
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();		
		return true;
	}

	@Override
	public void onBackPressed() {
    	String severity = EzeConstants.VALUE_SEVERITY_MANDATORY;
		try {
			severity = json.getString(EzeConstants.KEY_UPDATE_SEVERITY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Intent intent = new Intent();
    	if(severity.equals(EzeConstants.VALUE_SEVERITY_MANDATORY)) {
    		return;
    	} else {
    		setResult(RESULT_CANCELED, intent);
    	}
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_CANCELED && !installed) {
			String severity = EzeConstants.VALUE_SEVERITY_MANDATORY;
			if(severity.equals(EzeConstants.VALUE_SEVERITY_MANDATORY)){
				return;
			}
			Intent intent = new Intent();
			setResult(RESULT_CANCELED, intent);
			finish();
			return;
		}
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();	
	}
	
	
}
