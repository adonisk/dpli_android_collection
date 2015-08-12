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

public class FetchPolicy extends EzetapBaseActivity {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	private android.os.Vibrator mVibrator;
	private android.app.Activity context;
	private String outputNamespace = "policyDetail";
	private CustomDialog btn_fetch_policy_Fetchingpolicydetailsdlg = null;
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
        setContentView(R.layout.scr_fetch_policy);
		    	        

		SetImageUtils.setImageForOrg(this, "image_header_logo", (String)ctx.get("loginresponse.orgCode"));
		
		
        	((EditText)findViewById(R.id.txt_fetch_policy_policy_no)).setHint("Policy Number");
		
        	((TextView) findViewById(R.id.lbl_ezetap_version)).setText(compute_lbl_ezetap_version().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_version)).setTypeface(typeface);
		


		btn_fetch_policy_Fetchingpolicydetailsdlg = new CustomDialog(this);
    	btn_fetch_policy_Fetchingpolicydetailsdlg.setDialogParameter(CustomDialog.PROGRESS_DIALOG, null, getResources().getString(R.string.str_btn_fetch_policy_Fetchingpolicydetails), null); ;
	mVibrator = (android.os.Vibrator) this.getSystemService(VIBRATOR_SERVICE);
	UIUtils.removeJSON("paymentDetail");
    }
    
	@Override
	protected void onStart(){
		super.onStart();
		EzetapUtils.checkForInitDevice(this);
	}
    
    public void btn_fetch_policyClicked(View v){
    	mVibrator.vibrate(50);
        if(findViewById(R.id.txt_fetch_policy_policy_no).isShown())
		ctx.put("policyDetail.policyNo", ((android.widget.TextView)findViewById(R.id.txt_fetch_policy_policy_no)).getText().toString());
				btn_fetch_policy_Fetchingpolicydetailsdlg.show();
				com.ezetap.android.api.caller.ApiCaller.preApiCall("fetchPolicy", EzetapUIContext.getContext().getJSON("policyDetail"), context);
				com.ezetap.android.api.caller.ApiCaller.callApi("fetchPolicy", EzetapUIContext.getContext().getJSON("policyDetail"), context, 100);
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
					com.ezetap.android.api.caller.ApiCaller.postApiCall("fetchPolicy", apiResult, this);				
					ctx.forcePut("fetchPolicyresponse", apiResult);
					sendResponseTOJS("fetchPolicy", apiResult);
					Intent intent = new Intent(this, com.dpli.ReviewPolicy.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					if(data != null ) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("fetchPolicy", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
						sendResponseTOJS("fetchPolicy", apiResult);
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
			btn_fetch_policy_Fetchingpolicydetailsdlg.dismiss();
		}

		if(requestCode == 25){
			try {
				if(resultCode == 2001) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("txnHistory", apiResult, this);				
					ctx.forcePut("txnHistoryresponse", apiResult);
					Intent intent = new Intent(this, com.dpli.TransactionHistory.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					if(data != null) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("txnHistory", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
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

		if(requestCode == 26){
			try {
				if(resultCode == 2001) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("initDevice", apiResult, this);				
					ctx.forcePut("initDeviceresponse", apiResult);
				} else {
					if(data != null) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("initDevice", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
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


		if(requestCode == 28){
			try {
				if(resultCode == 2001) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("logout", apiResult, this);				
					ctx.forcePut("logoutresponse", apiResult);
					Intent intent = new Intent(this, com.dpli.LogintoDPLI.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					if(data != null) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("logout", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
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
	
	private String compute_lbl_ezetap_version() {
		try {
			return com.ezetap.android.utils.UIUtils.format(EzetapUtils.getVersionName(this));
		}catch (Exception e) {
			return "";
		}
	}
	

	private class txt_fetch_policy_policy_no implements android.text.TextWatcher {
		@Override
		public void afterTextChanged(android.text.Editable arg0) {
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		try{
			ctx.put("policyDetail.policyNo", ((EditText) findViewById(R.id.txt_fetch_policy_policy_no)).getText().toString());
			if(UIUtils.getTextLength(FetchPolicy.this, R.id.txt_fetch_policy_policy_no)>0) {         UIUtils.enableOrDisableControl(FetchPolicy.this, R.id.btn_fetch_policy, false);        } else {         UIUtils.enableOrDisableControl(FetchPolicy.this, R.id.btn_fetch_policy, true);        }
		} catch(Exception e){
			}
		}
	}

	public void onBackPressed(){
		 super.onBackPressed();
	 }
	 
	 
	 @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
			  menu.add(Menu.NONE, 1234, 1, 
			     wrapInSpan(getResources().getString(R.string.str_mitem_ezetap_pmt_history)))
		        .setTitleCondensed(getResources().getString(R.string.str_mitem_ezetap_pmt_history));
			  menu.add(Menu.NONE, 1234, 1, 
			     wrapInSpan(getResources().getString(R.string.str_mitem_ezetap_init_device)))
		        .setTitleCondensed(getResources().getString(R.string.str_mitem_ezetap_init_device));
			  menu.add(Menu.NONE, 1234, 1, 
			     wrapInSpan(getResources().getString(R.string.str_mitem_ezetap_help)))
		        .setTitleCondensed(getResources().getString(R.string.str_mitem_ezetap_help));
			  menu.add(Menu.NONE, 1234, 1, 
			     wrapInSpan(getResources().getString(R.string.str_mitem_ezetap_logout)))
		        .setTitleCondensed(getResources().getString(R.string.str_mitem_ezetap_logout));
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
		 if(title.equals(getResources().getString(R.string.str_mitem_ezetap_pmt_history)))
		 {
				com.ezetap.android.api.caller.ApiCaller.preApiCall("txnHistory", null, this);
				com.ezetap.android.api.caller.ApiCaller.callApi("txnHistory", null, this, 25);
		}
		 if(title.equals(getResources().getString(R.string.str_mitem_ezetap_init_device)))
		 {
				com.ezetap.android.api.caller.ApiCaller.preApiCall("initDevice", null, this);
				com.ezetap.android.api.caller.ApiCaller.callApi("initDevice", null, this, 26);
		}
		 if(title.equals(getResources().getString(R.string.str_mitem_ezetap_help)))
		 {
				Intent intent = new Intent(this, com.dpli.About.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
		}
		 if(title.equals(getResources().getString(R.string.str_mitem_ezetap_logout)))
		 {
				com.ezetap.android.api.caller.ApiCaller.preApiCall("logout", null, this);
				com.ezetap.android.api.caller.ApiCaller.callApi("logout", null, this, 28);
		}
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