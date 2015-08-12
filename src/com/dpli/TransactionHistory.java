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

public class TransactionHistory extends EzetapBaseListActivity {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	private Vibrator mVibrator;
	private static EzetapBaseListActivity activity;
	com.dpli.TxnHistoryRow row_txn_history;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.scr_txn_history);
        mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		if(EzetapUtils.getArrayLength("txnHistoryresponse.summary") < 1)
		findViewById(R.id.grp_txn_history_sub_header_2).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_history_sub_header_2).setVisibility(View.VISIBLE);
        	((TextView) findViewById(R.id.lbl_txn_history_date)).setText((String)ctx.get("txnSummary.date"));
		 	((TextView)findViewById(R.id.lbl_txn_history_total_count)).setText(ctx.get("txnHistoryresponse.count", 1).toString());
        	((TextView) findViewById(R.id.lbl_txn_history_currency_code)).setText((String)ctx.get("loginresponse.currencyCode"));
        	((TextView)findViewById(R.id.lbl_txn_history_netamount)).setText(ctx.get("txnHistoryresponse.netAmount", 0.0).toString());
		row_txn_history = new com.dpli.TxnHistoryRow(this, R.layout.row_txn_history, mVibrator);
		setListAdapter(row_txn_history);
	}
	
	public static EzetapBaseListActivity getActivity() {
		return activity;
	}
	
		 public void grp_txn_history_sub_header_2Clicked(View v){
	 	mVibrator.vibrate(50);
				Intent intent = new Intent(this, com.dpli.Summary.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			//((BaseAdapter)getListAdapter()).notifyDataSetChanged();
		}
	public void btn_date_txn_historyClicked(View v){
		mVibrator.vibrate(50);
			Intent intent = new Intent(this, CalendarView.class);
			startActivityForResult(intent, 899999+200);
		//((BaseAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
	mVibrator.vibrate(50);
	if(getListAdapter().equals(row_txn_history)){
	if(findViewById(R.id.lbl_txn_price_currency_code).isShown())
	ctx.put(EzetapUtils.setArrayIndexPosition("loginresponse.currencyCode", position), ((android.widget.TextView)v.findViewById(R.id.lbl_txn_price_currency_code)).getText().toString());
	if(findViewById(R.id.lbl_txn_price_currency_code_cashback).isShown())
	ctx.put(EzetapUtils.setArrayIndexPosition("loginresponse.currencyCode", position), ((android.widget.TextView)v.findViewById(R.id.lbl_txn_price_currency_code_cashback)).getText().toString());
	}
	if(getListAdapter().equals(row_txn_history)){
			com.ezetap.android.api.caller.ApiCaller.preApiCall("txnDetail", (JSONObject)EzetapUIContext.getContext().get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[]", position)), this);
			com.ezetap.android.api.caller.ApiCaller.callApi("txnDetail", (JSONObject)EzetapUIContext.getContext().get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[]", position)), this, 300);
		
		return;
	}
	}
	 @Override
	public  void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	 if(requestCode == 899999 + 200){
		    	try {
					if(resultCode == 2001) {
						org.json.JSONObject apiResult = null;
						if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA)) {
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
							ctx.forcePut("calendarresponse", apiResult);
						}
			com.ezetap.android.api.caller.ApiCaller.preApiCall("txnHistory", apiResult, this);
			com.ezetap.android.api.caller.ApiCaller.callApi("txnHistory", apiResult, this, 200);
					}
				} catch(org.json.JSONException e) {
					e.printStackTrace();
				}
				return;
			}
	

		if(requestCode == 200){
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
					if(data != null  && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA)){
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("txnHistory", apiResult, this);
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

	if(getListAdapter().equals(row_txn_history)){
	    if(requestCode == 300){
	    	try {
				if(resultCode == 2001) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("txnDetail", apiResult, this);
					ctx.forcePut("txnDetailresponse", apiResult);
					Intent intent = new Intent(this, com.dpli.TransactionDetail.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}else {
					if(data != null){
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("txnDetail", apiResult, this);
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
			return;
		}
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
        	((TextView) findViewById(R.id.lbl_txn_history_date)).setText((String)ctx.get("txnSummary.date"));
		 	((TextView)findViewById(R.id.lbl_txn_history_total_count)).setText(ctx.get("txnHistoryresponse.count", 1).toString());
        	((TextView) findViewById(R.id.lbl_txn_history_currency_code)).setText((String)ctx.get("loginresponse.currencyCode"));
        	((TextView)findViewById(R.id.lbl_txn_history_netamount)).setText(ctx.get("txnHistoryresponse.netAmount", 0.0).toString());
      }
      
      
}
