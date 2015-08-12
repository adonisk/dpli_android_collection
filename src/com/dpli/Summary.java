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

public class Summary extends EzetapBaseListActivity {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	private Vibrator mVibrator;
	private static EzetapBaseListActivity activity;
	com.dpli.TxnSummaryRow row_txn_summary;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.scr_txn_summary_screen);
        mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
			SetImageUtils.setImageForOrg(this, "image_header_logo", (String)ctx.get("loginresponse.orgCode"));
        	((TextView) findViewById(R.id.lbl_ezetap_version)).setText(compute_lbl_ezetap_version().toString());
		row_txn_summary = new com.dpli.TxnSummaryRow(this, R.layout.row_txn_summary, mVibrator);
		setListAdapter(row_txn_summary);
	}
	
	public static EzetapBaseListActivity getActivity() {
		return activity;
	}
	
		 public void btn_txn_summary_backClicked(View v){
	 	mVibrator.vibrate(50);
				finish();
		//	((BaseAdapter)getListAdapter()).notifyDataSetChanged();
		}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
	mVibrator.vibrate(50);
	if(getListAdapter().equals(row_txn_summary)){
	if(findViewById(R.id.lbl_txn_summary_currency_code).isShown())
	ctx.put(EzetapUtils.setArrayIndexPosition("loginresponse.currencyCode", position), ((android.widget.TextView)v.findViewById(R.id.lbl_txn_summary_currency_code)).getText().toString());
	}
	if(getListAdapter().equals(row_txn_summary)){
		return;
	}
	}
	 @Override
	public  void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	

	if(getListAdapter().equals(row_txn_summary)){
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
		inflater.inflate(com.dpli.R.menu.menu_summary_screen, menu);
		return true;
	}

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	    {
	    
		 String title = item.getTitle().toString();
		 boolean retVal = false;
		 return retVal;

	}
      
}
