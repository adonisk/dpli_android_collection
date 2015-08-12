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

public class BankSelection extends EzetapBaseListActivity {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	private Vibrator mVibrator;
	private static EzetapBaseListActivity activity;
	com.dpli.BankRow row_bank;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.scr_bank_selection);
        mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
			SetImageUtils.setImageForOrg(this, "image_header_logo", (String)ctx.get("loginresponse.orgCode"));
        	((TextView) findViewById(R.id.lbl_ezetap_version)).setText(compute_lbl_ezetap_version().toString());
		row_bank = new com.dpli.BankRow(this, R.layout.row_bank, mVibrator);
		setListAdapter(row_bank);
	}
	
	public static EzetapBaseListActivity getActivity() {
		return activity;
	}
	
	
	protected void onListItemClick(ListView l, View v, int position, long id){
	mVibrator.vibrate(50);
	if(getListAdapter().equals(row_bank)){
	if(findViewById(R.id.lbl_li_bs_bank_name).isShown())
	ctx.put(EzetapUtils.setArrayIndexPosition("instrumentDetail.bankName", position), ((android.widget.TextView)v.findViewById(R.id.lbl_li_bs_bank_name)).getText().toString());
	}
	if(getListAdapter().equals(row_bank)){
			Intent intent = new Intent(this, com.dpli.ChequeDetails.class);
			startActivity(intent);
		return;
	}
	}
	 @Override
	public  void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	
	if(getListAdapter().equals(row_bank)){
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
	 }
	
	 @Override
	 public void onResume(){
		 super.onResume();
		// ((BaseAdapter)getListAdapter()).notifyDataSetChanged();
	 }
	 
	 public void redrawComponents(){
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
