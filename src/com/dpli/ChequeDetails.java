package com.dpli;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Paint;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.CustomDialog;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.UIUtils;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.*;
import com.ezetap.android.utils.SetImageUtils;

public class ChequeDetails extends EzetapBaseListActivity {
	private String outputNamespace = "instrumentDetail";

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	private Vibrator mVibrator;
	private static EzetapBaseListActivity activity;
	com.dpli.NoRow lst_item_bank_row;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.scr_cheque_details);
        mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		if((EzetapUtils.getArrayLength("loginresponse.setting.chequePaymentBankNames") < 1))
		findViewById(R.id.grp_cheque_details_bank_name).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_cheque_details_bank_name).setVisibility(View.VISIBLE);
			SetImageUtils.setImageForOrg(this, "image_header_logo", (String)ctx.get("loginresponse.orgCode"));
        	((TextView) findViewById(R.id.lbl_cheque_details_main_title)).setText((String)ctx.get("instrumentDetail.view_title"));
        	((EditText) findViewById(R.id.txt_cheque_number)).setText((String)ctx.get("instrumentDetail.chequeNumber"));
        	((EditText)findViewById(R.id.txt_cheque_number)).setHint((String)ctx.get("instrumentDetail.view_number"));
        	((EditText) findViewById(R.id.txt_cheque_details_bank_name)).setText((String)ctx.get("instrumentDetail.bankName"));
        	((EditText)findViewById(R.id.txt_cheque_details_bank_name)).setHint("Bank Name");
        	((EditText) findViewById(R.id.txt_cheque_bank_code)).setText((String)ctx.get("instrumentDetail.bankCode"));
        	((EditText)findViewById(R.id.txt_cheque_bank_code)).setHint("Bank IFSC Code");
        	((EditText) findViewById(R.id.txt_cheque_details_date)).setText((String)ctx.get("calendarresponse.startDate"));
        	((EditText)findViewById(R.id.txt_cheque_details_date)).setHint((String)ctx.get("instrumentDetail.view_date"));
        	((EditText) findViewById(R.id.txt_cheque_clearing_type)).setText((String)ctx.get("instrumentDetail.clearingType"));
        	((EditText)findViewById(R.id.txt_cheque_clearing_type)).setHint("Clearing Type");
        	((TextView) findViewById(R.id.lbl_instrument_type)).setText((String)ctx.get("instrumentDetail.instrumentType"));
        	if(true)
        	((TextView) findViewById(R.id.lbl_instrument_type)).setVisibility(View.GONE);
        	else
        	((TextView) findViewById(R.id.lbl_instrument_type)).setVisibility(View.VISIBLE);
        	((TextView) findViewById(R.id.lbl_ezetap_version)).setText(compute_lbl_ezetap_version().toString());
		lst_item_bank_row = new com.dpli.NoRow(this, R.layout.lst_item_bank_row, mVibrator);
		setListAdapter(lst_item_bank_row);
    UIUtils.enableOrDisableControl(this, R.id.txt_cheque_details_date, true);
	}
	
	public static EzetapBaseListActivity getActivity() {
		return activity;
	}
	
		 public void btn_cheque_details_bank_nameClicked(View v){
	 	mVibrator.vibrate(50);
		ctx.put("instrumentDetail.chequeNumber", ((android.widget.TextView)findViewById(R.id.txt_cheque_number)).getText().toString());
		ctx.put("instrumentDetail.bankName", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_bank_name)).getText().toString());
		ctx.put("instrumentDetail.bankCode", ((android.widget.TextView)findViewById(R.id.txt_cheque_bank_code)).getText().toString());
		ctx.put("instrumentDetail.chequeDate", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_date)).getText().toString());
		ctx.put("instrumentDetail.clearingType", ((android.widget.TextView)findViewById(R.id.txt_cheque_clearing_type)).getText().toString());
		ctx.put("instrumentDetail.instrumentType", ((android.widget.TextView)findViewById(R.id.lbl_instrument_type)).getText().toString());
				Intent intent = new Intent(this, com.dpli.BankSelection.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
		//	((BaseAdapter)getListAdapter()).notifyDataSetChanged();
		}
		 public void btn_cheque_details_nextClicked(View v){
	 	mVibrator.vibrate(50);
		ctx.put("instrumentDetail.chequeNumber", ((android.widget.TextView)findViewById(R.id.txt_cheque_number)).getText().toString());
		ctx.put("instrumentDetail.bankName", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_bank_name)).getText().toString());
		ctx.put("instrumentDetail.bankCode", ((android.widget.TextView)findViewById(R.id.txt_cheque_bank_code)).getText().toString());
		ctx.put("instrumentDetail.chequeDate", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_date)).getText().toString());
		ctx.put("instrumentDetail.clearingType", ((android.widget.TextView)findViewById(R.id.txt_cheque_clearing_type)).getText().toString());
		ctx.put("instrumentDetail.instrumentType", ((android.widget.TextView)findViewById(R.id.lbl_instrument_type)).getText().toString());
				com.ezetap.android.api.caller.ApiCaller.preApiCall("payCheque", EzetapUIContext.getContext().getJSON("paymentDetail"), this);
				com.ezetap.android.api.caller.ApiCaller.callApi("payCheque", EzetapUIContext.getContext().getJSON("paymentDetail"), this, 400);
		//	((BaseAdapter)getListAdapter()).notifyDataSetChanged();
		}
	public void btn_cheque_details_dateClicked(View v){
		mVibrator.vibrate(50);
		ctx.put("instrumentDetail.chequeNumber", ((android.widget.TextView)findViewById(R.id.txt_cheque_number)).getText().toString());
		ctx.put("instrumentDetail.bankName", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_bank_name)).getText().toString());
		ctx.put("instrumentDetail.bankCode", ((android.widget.TextView)findViewById(R.id.txt_cheque_bank_code)).getText().toString());
		ctx.put("instrumentDetail.chequeDate", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_date)).getText().toString());
		ctx.put("instrumentDetail.clearingType", ((android.widget.TextView)findViewById(R.id.txt_cheque_clearing_type)).getText().toString());
		ctx.put("instrumentDetail.instrumentType", ((android.widget.TextView)findViewById(R.id.lbl_instrument_type)).getText().toString());
			Intent intent = new Intent(this, CalendarView.class);
			startActivityForResult(intent, 899999+300);
		//((BaseAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
	mVibrator.vibrate(50);
	ctx.put("instrumentDetail.chequeNumber", ((android.widget.TextView)findViewById(R.id.txt_cheque_number)).getText().toString());
	ctx.put("instrumentDetail.bankName", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_bank_name)).getText().toString());
	ctx.put("instrumentDetail.bankCode", ((android.widget.TextView)findViewById(R.id.txt_cheque_bank_code)).getText().toString());
	ctx.put("instrumentDetail.chequeDate", ((android.widget.TextView)findViewById(R.id.txt_cheque_details_date)).getText().toString());
	ctx.put("instrumentDetail.clearingType", ((android.widget.TextView)findViewById(R.id.txt_cheque_clearing_type)).getText().toString());
	ctx.put("instrumentDetail.instrumentType", ((android.widget.TextView)findViewById(R.id.lbl_instrument_type)).getText().toString());
	if(getListAdapter().equals(lst_item_bank_row)){
	if(findViewById(R.id.lbl_bank_row).isShown())
	ctx.put(EzetapUtils.setArrayIndexPosition("instrumentDetail.bankCode", position), ((android.widget.TextView)v.findViewById(R.id.lbl_bank_row)).getText().toString());
	}
	if(getListAdapter().equals(lst_item_bank_row)){
			Intent intent = new Intent(this, com.dpli.ChequeDetails.class);
			startActivity(intent);
		return;
	}
	}
	 @Override
	public  void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	 if(requestCode == 899999 + 300){
		    	try {
					if(resultCode == 2001) {
						org.json.JSONObject apiResult = null;
						if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA)) {
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
							ctx.forcePut("calendarresponse", apiResult);
						}
					Intent intent = new Intent(this, com.dpli.ChequeDetails.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					}
				} catch(org.json.JSONException e) {
					e.printStackTrace();
				}
				return;
			}
	

		if(requestCode == 400){
			try {
				if(resultCode == 2001) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("payCheque", apiResult, this);				
					ctx.forcePut("payChequeresponse", apiResult);
					if((EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.smsReceiptEnabledForCheque"), false)||EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.emailReceiptEnabled"), false))){
						Intent intent = new Intent(this, com.dpli.SendReceipt.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}else{
						Intent intent = new Intent(this, com.dpli.FetchPolicy.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					
				} else {
					if(data != null  && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA)){
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("payCheque", apiResult, this);
						if(apiResult != null && apiResult.has("errorCode")) {
							String errorCode = apiResult.getString("errorCode");
							if(errorCode != null && errorCode.equals("SESSION_EXPIRED")) {
								JUtils.startLauncherActivity(this);
							}
						}						
					}
					com.ezetap.android.utils.EzetapUtils.showError(data , this);
				}
			} catch(org.json.JSONException e) {
				e.printStackTrace();
			}
			return;
		}


	if(getListAdapter().equals(lst_item_bank_row)){
	}
		if(requestCode == 80){
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

		if(requestCode == 81){
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


		if(requestCode == 83){
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
	 public void onStart(){
		 super.onStart();
	        if(((String)ctx.get("paymentDetail.bankName")).equalsIgnoreCase("other")) {     UIUtils.enableOrDisableControl(this, R.id.txt_cheque_details_bank_name, false);    } else {     UIUtils.enableOrDisableControl(this, R.id.txt_cheque_details_bank_name, true);    }
	        ((BaseAdapter)getListAdapter()).notifyDataSetChanged();
	 }
	
	 @Override
	 public void onResume(){
		 super.onResume();
		// ((BaseAdapter)getListAdapter()).notifyDataSetChanged();
	 }
	 
	 public void redrawComponents(){
        	((TextView) findViewById(R.id.lbl_cheque_details_main_title)).setText((String)ctx.get("instrumentDetail.view_title"));
        	((EditText) findViewById(R.id.txt_cheque_number)).setText((String)ctx.get("instrumentDetail.chequeNumber"));
        	((EditText) findViewById(R.id.txt_cheque_details_bank_name)).setText((String)ctx.get("instrumentDetail.bankName"));
        	((EditText) findViewById(R.id.txt_cheque_bank_code)).setText((String)ctx.get("instrumentDetail.bankCode"));
        	((EditText) findViewById(R.id.txt_cheque_details_date)).setText((String)ctx.get("calendarresponse.startDate"));
        	((EditText) findViewById(R.id.txt_cheque_clearing_type)).setText((String)ctx.get("instrumentDetail.clearingType"));
        	((TextView) findViewById(R.id.lbl_instrument_type)).setText((String)ctx.get("instrumentDetail.instrumentType"));
        	if(true)
        	((TextView) findViewById(R.id.lbl_instrument_type)).setVisibility(View.GONE);
        	else
        	((TextView) findViewById(R.id.lbl_instrument_type)).setVisibility(View.VISIBLE);
        	((TextView) findViewById(R.id.lbl_ezetap_version)).setText(compute_lbl_ezetap_version().toString());
      }
      
	 
	 @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(com.dpli.R.menu.menu_ezetap, menu);
		return true;
	}

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	    {
	    
		 String title = item.getTitle().toString();
		 boolean retVal = false;
		 if(title.equals("Transaction History"))
		 {
				com.ezetap.android.api.caller.ApiCaller.preApiCall("txnHistory", null, this);
				com.ezetap.android.api.caller.ApiCaller.callApi("txnHistory", null, this, 80);
		}
		 if(title.equals("Prepare Device"))
		 {
				com.ezetap.android.api.caller.ApiCaller.preApiCall("initDevice", null, this);
				com.ezetap.android.api.caller.ApiCaller.callApi("initDevice", null, this, 81);
		}
		 if(title.equals("About"))
		 {
				Intent intent = new Intent(this, com.dpli.About.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
		}
		 if(title.equals("Logout"))
		 {
				com.ezetap.android.api.caller.ApiCaller.preApiCall("logout", null, this);
				com.ezetap.android.api.caller.ApiCaller.callApi("logout", null, this, 83);
		}
		 return retVal;

	}
      
}
