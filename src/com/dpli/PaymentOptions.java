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

public class PaymentOptions extends EzetapBaseActivity {

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
        setContentView(R.layout.scr_payment_options);
		    	        

		SetImageUtils.setImageForOrg(this, "image_header_logo", (String)ctx.get("loginresponse.orgCode"));
		
		
        	((TextView) findViewById(R.id.lbl_payment_options_currency_code)).setText((String)ctx.get("loginresponse.currencyCode"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_payment_options_currency_code)).setTypeface(typeface);
		
        	((TextView)findViewById(R.id.lbl_payment_options_amount)).setText(ctx.get("paymentDetail.amount", 0.0).toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_payment_options_amount)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_version)).setText(compute_lbl_ezetap_version().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_version)).setTypeface(typeface);
		


    	if(!EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.cashPaymentEnabled"),false))
    	((Button) findViewById(R.id.btn_cash_pymt)).setVisibility(View.GONE);
    	else
    	((Button) findViewById(R.id.btn_cash_pymt)).setVisibility(View.VISIBLE);


    	if((!EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.chequePaymentEnabled"),false)))
    	((Button) findViewById(R.id.btn_cheque_pymt)).setVisibility(View.GONE);
    	else
    	((Button) findViewById(R.id.btn_cheque_pymt)).setVisibility(View.VISIBLE);

    	if((!EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.chequePaymentEnabled"),false)))
    	((Button) findViewById(R.id.btn_dd_pymt)).setVisibility(View.GONE);
    	else
    	((Button) findViewById(R.id.btn_dd_pymt)).setVisibility(View.VISIBLE);
	mVibrator = (android.os.Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    }
    
	@Override
	protected void onStart(){
		super.onStart();
		EzetapUtils.checkForInitDevice(this);
	}
    
    public void btn_cash_pymtClicked(View v){
    	mVibrator.vibrate(50);
				com.ezetap.android.api.caller.ApiCaller.preApiCall("payCash", EzetapUIContext.getContext().getJSON("paymentDetail"), context);
				com.ezetap.android.api.caller.ApiCaller.callApi("payCash", EzetapUIContext.getContext().getJSON("paymentDetail"), context, 100);
				}			
    public void btn_card_pymtClicked(View v){
    	mVibrator.vibrate(50);
				com.ezetap.android.api.caller.ApiCaller.preApiCall("payCard", EzetapUIContext.getContext().getJSON("paymentDetail"), context);
				com.ezetap.android.api.caller.ApiCaller.callApi("payCard", EzetapUIContext.getContext().getJSON("paymentDetail"), context, 200);
				}			
    public void btn_cheque_pymtClicked(View v){
    	mVibrator.vibrate(50);
			if(com.dpli.utils.DPLIHelper.setInstrumentType("cheque")) {
				Intent intent = new Intent(context, com.dpli.ChequeDetails.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
}
				else {
				Intent intent = new Intent(context, com.dpli.ChequeDetails.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				}			
}
    public void btn_dd_pymtClicked(View v){
    	mVibrator.vibrate(50);
			if(com.dpli.utils.DPLIHelper.setInstrumentType("demandDraft")) {
				Intent intent = new Intent(context, com.dpli.ChequeDetails.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
}
				else {
				Intent intent = new Intent(context, com.dpli.ChequeDetails.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				}			
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
					com.ezetap.android.api.caller.ApiCaller.postApiCall("payCash", apiResult, this);				
					ctx.forcePut("payCashresponse", apiResult);
					sendResponseTOJS("payCash", apiResult);
					if(!(EzetapUtils.getBooleanValue(ctx.get("payCashresponse.manualReceipt"),false))&&(EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.smsReceiptEnabledForCash"), false)||EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.emailReceiptEnabled"), false))){
						Intent intent = new Intent(this, com.dpli.SendReceipt.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}else{
						Intent intent = new Intent(this, com.dpli.FetchPolicy.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				} else {
					if(data != null ) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("payCash", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
						sendResponseTOJS("payCash", apiResult);
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

		if(requestCode == 200){
			try {
				if(resultCode == 2001 || resultCode == RESULT_OK) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("payCard", apiResult, this);				
					ctx.forcePut("payCardresponse", apiResult);
					sendResponseTOJS("payCard", apiResult);
					if(!(EzetapUtils.getBooleanValue(ctx.get("payCardresponse.manualReceipt"),false))&&(EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.smsReceiptEnabled"), true)||EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.emailReceiptEnabled"), false))){
						Intent intent = new Intent(this, com.dpli.SendReceipt.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}else{
						Intent intent = new Intent(this, com.dpli.FetchPolicy.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				} else {
					if(data != null ) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("payCard", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
						sendResponseTOJS("payCard", apiResult);
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