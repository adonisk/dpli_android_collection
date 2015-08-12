package com.dpli;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Paint;

import android.webkit.WebView;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.EzeConstants.Channel;
import com.ezetap.utils.EzeConstants.ChannelSelectionMode;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.CustomDialog;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.SetImageUtils;
import com.ezetap.android.utils.UIUtils;
import com.ezetap.utils.*;

public class About extends EzetapBaseActivity {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	private android.os.Vibrator mVibrator;
	private android.app.Activity context;
	//android.graphics.Typeface typeface;
	private boolean JSResponse = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
      //  typeface = android.graphics.Typeface.createFromAsset(getAssets(),"font.ttf");
       // if(typeface != null) {
        	java.util.Locale myLocale = JUtils.getLocale(this);
			java.util.Locale.setDefault(myLocale);
			android.content.res.Configuration config = new android.content.res.Configuration();
		    config.locale = myLocale;
		    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
       // }
        setContentView(R.layout.scr_ezetap_about_screen);
		    	        

		
		
        	((TextView) findViewById(R.id.lbl_ezetap_about_app_name)).setText(compute_lbl_ezetap_about_app_name().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_about_app_name)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_about_app_version)).setText(compute_lbl_ezetap_about_app_version().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_about_app_version)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_about_genmode_value)).setText(compute_lbl_ezetap_about_genmode_value().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_about_genmode_value)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_about_contact_us_label_2)).setText((String)ctx.get("fetchAboutresponse.phone", "080 67678787"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_about_contact_us_label_2)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_about_contact_us_label_3)).setText((String)ctx.get("fetchAboutresponse.timing", "We are available 24x7"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_about_contact_us_label_3)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_about_contact_us_label_4)).setText((String)ctx.get("fetchAboutresponse.website", "Visit us at www.ezetap.com"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_about_contact_us_label_4)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_about_imei_no)).setText(compute_lbl_ezetap_about_imei_no().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_about_imei_no)).setTypeface(typeface);


	mVibrator = (android.os.Vibrator) this.getSystemService(VIBRATOR_SERVICE);
	EzetapUtils.setJSONValue("fetchAboutresponse.phone", "080 67678787");
    }
    
	@Override
	protected void onStart(){
		super.onStart();
	}
    
    public void btn_ezetap_about_us_callClicked(View v){
    	mVibrator.vibrate(50);
				com.ezetap.android.api.caller.ApiCaller.preApiCall("callNumber", EzetapUIContext.getContext().getJSON("fetchAboutresponse"), context);
				com.ezetap.android.api.caller.ApiCaller.callApi("callNumber", EzetapUIContext.getContext().getJSON("fetchAboutresponse"), context, 100);
				}			
    @Override
	public  void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 100){
			try {
				if(resultCode == 2001 || resultCode == RESULT_OK) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("callNumber", apiResult, this);				
					ctx.forcePut("callNumberresponse", apiResult);
					sendResponseTOJS("callNumber", apiResult);
					Intent intent = new Intent(this, com.dpli.About.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					if(data != null ) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("callNumber", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
						sendResponseTOJS("callNumber", apiResult);
						if(apiResult != null && apiResult.has("errorCode")) {
							String errorCode = apiResult.getString("errorCode");
							if(errorCode != null && errorCode.equals("SESSION_EXPIRED")) {
								JUtils.startLauncherActivity(this);
							}
						}
					}
				}
			} catch(org.json.JSONException e) {
				e.printStackTrace();
			}
		}

	}
	
	private String compute_lbl_ezetap_about_app_name() {
		try {
			return com.ezetap.android.utils.UIUtils.format(EzetapUtils.getAppName(this));
		}catch (Exception e) {
			return "";
		}
	}
	private String compute_lbl_ezetap_about_app_version() {
		try {
			return com.ezetap.android.utils.UIUtils.format(EzetapUtils.getVersionName(this));
		}catch (Exception e) {
			return "";
		}
	}
	private String compute_lbl_ezetap_about_genmode_value() {
		try {
			return com.ezetap.android.utils.UIUtils.format(com.ezetap.android.app.properties.EzetapProperties.genMode);
		}catch (Exception e) {
			return "";
		}
	}
	private String compute_lbl_ezetap_about_imei_no() {
		try {
			return com.ezetap.android.utils.UIUtils.format(EzetapUtils.getIMEI(this));
		}catch (Exception e) {
			return "";
		}
	}
	

	public void onBackPressed(){
		 super.onBackPressed();
	 }
	 
	 
	 @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return true;
	}
	 
 	 private CharSequence wrapInSpan(CharSequence value) {
	    android.text.SpannableStringBuilder sb = new android.text.SpannableStringBuilder(value);
	   // sb.setSpan(typeface, 0, value.length(), 0);
	    return sb;
	}
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	    {
	    
		 String title = item.getTitle().toString();
		 boolean retVal = false;
		 return retVal;
	    }

    
    private void sendResponseTOJS(String actionName, org.json.JSONObject apiResult) {
    	if(JSResponse) {
    		if(actionName.equalsIgnoreCase("payCard") ||
    		   actionName.equalsIgnoreCase("payCash") ||
    		   actionName.equalsIgnoreCase("payCheque")) {
    		    WebView webView = null;
    		}
    	}
 	}   
}