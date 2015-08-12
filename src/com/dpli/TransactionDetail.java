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

public class TransactionDetail extends EzetapBaseActivity {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	private android.os.Vibrator mVibrator;
	private android.app.Activity context;
	private String outputNamespace = "loginresponse";
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
        setContentView(R.layout.scr_txn_detail);
		    	        
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.customerName")))
		findViewById(R.id.grp_txn_detail_customer_name).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_customer_name).setVisibility(View.VISIBLE);
		if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
		findViewById(R.id.grp_txn_detail_merchant_name).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_merchant_name).setVisibility(View.VISIBLE);
		if(!(ctx.get("txnDetailresponse.amountCashBack").getClass().equals(Integer.class)     && ((Integer)ctx.get("txnDetailresponse.amountCashBack"))>0))
		findViewById(R.id.grp_txn_detail_amountCashBack).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_amountCashBack).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.emiTerm")))
		findViewById(R.id.grp_txn_detail_emi).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_emi).setVisibility(View.VISIBLE);
		if(!(ctx.get("txnDetailresponse.emiInterestRate").getClass().equals(Integer.class)     && ((Integer)ctx.get("txnDetailresponse.emiInterestRate"))>0))
		findViewById(R.id.grp_txn_detail_emiIr).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_emiIr).setVisibility(View.VISIBLE);
		if(!(ctx.get("txnDetailresponse.emiCashbackPercentage").getClass().equals(Integer.class)     && ((Integer)ctx.get("txnDetailresponse.emiCashbackPercentage"))>0))
		findViewById(R.id.grp_txn_detail_emiCb).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_emiCb).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.customerMobile")))
		findViewById(R.id.grp_txn_detail_mobile).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_mobile).setVisibility(View.VISIBLE);
		if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
		findViewById(R.id.grp_txn_detail_auth_code).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_auth_code).setVisibility(View.VISIBLE);
		if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
		findViewById(R.id.grp_txn_detail_terminal_id).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_terminal_id).setVisibility(View.VISIBLE);
		if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
		findViewById(R.id.grp_txn_detail_merchant_id).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_merchant_id).setVisibility(View.VISIBLE);
		if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
		findViewById(R.id.grp_txn_detail_invoice_num).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_invoice_num).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.rrNumber")))
		findViewById(R.id.grp_txn_detail_rrn).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_rrn).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.status")))
		findViewById(R.id.grp_txn_detail_status).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_status).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.txnTypeDesc")))
		findViewById(R.id.grp_txn_detail_txn_type_desc).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_txn_type_desc).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.chequeNumber")))
		findViewById(R.id.grp_txn_detail_cheque_number).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_cheque_number).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.bankCode")))
		findViewById(R.id.grp_txn_detail_bank_code).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_bank_code).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.bankName")))
		findViewById(R.id.grp_txn_detail_bank_name).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_bank_name).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.customerReceiptUrl")))
		findViewById(R.id.grp_txn_detail_customer_receipt_url).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_customer_receipt_url).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get("txnDetailresponse.customerEmail")))
		findViewById(R.id.grp_txn_detail_email).setVisibility(View.GONE);
		else
		findViewById(R.id.grp_txn_detail_email).setVisibility(View.VISIBLE);

		SetImageUtils.setImageForOrg(this, "image_header_logo", (String)ctx.get("loginresponse.orgCode"));
		
		
        	((TextView) findViewById(R.id.lbl_txn_detail_txn_id)).setText((String)ctx.get("txnDetailresponse.txnId"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_txn_id)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label)).setText((String)ctx.get("loginresponse.setting.orderNumberLabel"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label)).setTypeface(typeface);
        	if(!StringUtils.hasText((String)ctx.get("loginresponse.setting.orderNumberLabel")))
        	((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label)).setVisibility(View.GONE);
        	else
        	((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label)).setVisibility(View.VISIBLE);
		
        	if(!StringUtils.hasText((String)ctx.get("loginresponse.setting.orderNumberLabel")))
        	((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label_1)).setVisibility(View.GONE);
        	else
        	((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label_1)).setVisibility(View.VISIBLE);
		
        	if(StringUtils.hasText((String)ctx.get("loginresponse.setting.orderNumberLabel")))
        	((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label_old)).setVisibility(View.GONE);
        	else
        	((TextView) findViewById(R.id.lbl_txn_detail_shipment_id_label_old)).setVisibility(View.VISIBLE);
		
		 	((TextView)findViewById(R.id.lbl_txn_detail_shipment_id)).setText(ctx.get("txnDetailresponse.externalRefNumber", 1).toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_shipment_id)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_customer_name)).setText((String)ctx.get("txnDetailresponse.customerName"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_customer_name)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_merchant_name)).setText((String)ctx.get("txnDetailresponse.merchantName"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_merchant_name)).setTypeface(typeface);
		
        	if(((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
        	((ImageView) findViewById(R.id.img_txn_detail_payment_type)).setVisibility(View.GONE);
        	else
        	((ImageView) findViewById(R.id.img_txn_detail_payment_type)).setVisibility(View.VISIBLE);
        	SetImageUtils.setImageForPaymentType(this, "img_txn_detail_payment_type", (String) ctx.get("txnDetailresponse.paymentMode"));
		
        	if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
        	((ImageView) findViewById(R.id.img_txn_detail_card_type)).setVisibility(View.GONE);
        	else
        	((ImageView) findViewById(R.id.img_txn_detail_card_type)).setVisibility(View.VISIBLE);
        	SetImageUtils.setImageForCardType(this, "img_txn_detail_card_type", (String)ctx.get("txnDetailresponse.cardType"));
		
        	if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
        	((TextView) findViewById(R.id.lbl_txn_detail_card_prefix)).setVisibility(View.GONE);
        	else
        	((TextView) findViewById(R.id.lbl_txn_detail_card_prefix)).setVisibility(View.VISIBLE);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_card_last_four)).setText((String)ctx.get("txnDetailresponse.cardLastFourDigit"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_card_last_four)).setTypeface(typeface);
        	if(!((String)ctx.get("txnDetailresponse.paymentMode")).equals("CARD"))
        	((TextView) findViewById(R.id.lbl_txn_detail_card_last_four)).setVisibility(View.GONE);
        	else
        	((TextView) findViewById(R.id.lbl_txn_detail_card_last_four)).setVisibility(View.VISIBLE);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_price_currency_code)).setText((String)ctx.get("loginresponse.currencyCode"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_price_currency_code)).setTypeface(typeface);
		
        	((TextView)findViewById(R.id.lbl_txn_detail_amount)).setText(ctx.get("txnDetailresponse.amount", 0.0).toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_amount)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_price_currency_code_cashback)).setText((String)ctx.get("loginresponse.currencyCode"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_price_currency_code_cashback)).setTypeface(typeface);
		
        	((TextView)findViewById(R.id.lbl_txn_detail_amountCashBack)).setText(ctx.get("txnDetailresponse.amountCashBack", 0.0).toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_amountCashBack)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_emi)).setText((String)ctx.get("txnDetailresponse.emiTerm"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_emi)).setTypeface(typeface);
		
        	((TextView)findViewById(R.id.lbl_txn_detail_emi_interest)).setText(ctx.get("txnDetailresponse.emiInterestRate", 0.0).toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_emi_interest)).setTypeface(typeface);
		
        	((TextView)findViewById(R.id.lbl_txn_detail_emi_cashback)).setText(ctx.get("txnDetailresponse.emiCashbackPercentage", 0.0).toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_emi_cashback)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_mobile_number)).setText((String)ctx.get("txnDetailresponse.customerMobile"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_mobile_number)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_creation_time)).setText(compute_lbl_txn_detail_creation_time().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_creation_time)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_authcode)).setText((String)ctx.get("txnDetailresponse.authCode"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_authcode)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_terminal_id)).setText((String)ctx.get("txnDetailresponse.tid"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_terminal_id)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_merchant_id)).setText((String)ctx.get("txnDetailresponse.mid"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_merchant_id)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_invoice_number)).setText((String)ctx.get("txnDetailresponse.invoiceNumber"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_invoice_number)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_rrn)).setText((String)ctx.get("txnDetailresponse.rrNumber"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_rrn)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_status)).setText((String)ctx.get("txnDetailresponse.status"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_status)).setTypeface(typeface);
        	((TextView) findViewById(R.id.lbl_txn_detail_status)).setTextColor(Color.parseColor(EzetapUtils.getColorForTxnStatus((String)ctx.get("txnDetailresponse.status"))));
		
        	((TextView) findViewById(R.id.lbl_txn_detail_type_desc)).setText((String)ctx.get("txnDetailresponse.txnTypeDesc"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_type_desc)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_cheque_number)).setText((String)ctx.get("txnDetailresponse.chequeNumber"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_cheque_number)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_bank_code)).setText((String)ctx.get("txnDetailresponse.bankCode"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_bank_code)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_bank_name)).setText((String)ctx.get("txnDetailresponse.bankName"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_bank_name)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_customer_receipt_url)).setText((String)ctx.get("txnDetailresponse.customerReceiptUrl"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_customer_receipt_url)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_txn_detail_email)).setText((String)ctx.get("txnDetailresponse.customerEmail"));
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_txn_detail_email)).setTypeface(typeface);
		
        	((TextView) findViewById(R.id.lbl_ezetap_version)).setText(compute_lbl_ezetap_version().toString());
        	//if(typeface != null)
        		//((TextView) findViewById(R.id.lbl_ezetap_version)).setTypeface(typeface);
		


    	if(!((Boolean)ctx.get("loginresponse.voidAllowed"))||!((Boolean)ctx.get("txnDetailresponse.voidable")))
    	((Button) findViewById(R.id.btn_todo_detail_void)).setVisibility(View.GONE);
    	else
    	((Button) findViewById(R.id.btn_todo_detail_void)).setVisibility(View.VISIBLE);

    	if(!EzetapUtils.shouldSendReceipt((String)ctx.get("txnDetailresponse.paymentMode")))
    	((Button) findViewById(R.id.btn_txn_detail_send_receipt)).setVisibility(View.GONE);
    	else
    	((Button) findViewById(R.id.btn_txn_detail_send_receipt)).setVisibility(View.VISIBLE);

    	if(!((Boolean)ctx.get("txnDetailresponse.signReqd")))
    	((Button) findViewById(R.id.btn_todo_detail_attach_sign)).setVisibility(View.GONE);
    	else
    	((Button) findViewById(R.id.btn_todo_detail_attach_sign)).setVisibility(View.VISIBLE);
	mVibrator = (android.os.Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    }
    
	@Override
	protected void onStart(){
		super.onStart();
	}
    
    public void btn_todo_detail_voidClicked(View v){
    	mVibrator.vibrate(50);
        if(findViewById(R.id.lbl_txn_detail_price_currency_code).isShown())
		ctx.put("loginresponse.currencyCode", ((android.widget.TextView)findViewById(R.id.lbl_txn_detail_price_currency_code)).getText().toString());
        if(findViewById(R.id.lbl_txn_detail_price_currency_code_cashback).isShown())
		ctx.put("loginresponse.currencyCode", ((android.widget.TextView)findViewById(R.id.lbl_txn_detail_price_currency_code_cashback)).getText().toString());
				com.ezetap.android.api.caller.ApiCaller.preApiCall("void", EzetapUIContext.getContext().getJSON("txnDetailresponse"), context);
				com.ezetap.android.api.caller.ApiCaller.callApi("void", EzetapUIContext.getContext().getJSON("txnDetailresponse"), context, 100);
				}			
    public void btn_txn_detail_send_receiptClicked(View v){
    	mVibrator.vibrate(50);
        if(findViewById(R.id.lbl_txn_detail_price_currency_code).isShown())
		ctx.put("loginresponse.currencyCode", ((android.widget.TextView)findViewById(R.id.lbl_txn_detail_price_currency_code)).getText().toString());
        if(findViewById(R.id.lbl_txn_detail_price_currency_code_cashback).isShown())
		ctx.put("loginresponse.currencyCode", ((android.widget.TextView)findViewById(R.id.lbl_txn_detail_price_currency_code_cashback)).getText().toString());
				Intent intent = new Intent(context, com.dpli.ReSendReceipt.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				}			
    public void btn_todo_detail_attach_signClicked(View v){
    	mVibrator.vibrate(50);
        if(findViewById(R.id.lbl_txn_detail_price_currency_code).isShown())
		ctx.put("loginresponse.currencyCode", ((android.widget.TextView)findViewById(R.id.lbl_txn_detail_price_currency_code)).getText().toString());
        if(findViewById(R.id.lbl_txn_detail_price_currency_code_cashback).isShown())
		ctx.put("loginresponse.currencyCode", ((android.widget.TextView)findViewById(R.id.lbl_txn_detail_price_currency_code_cashback)).getText().toString());
				com.ezetap.android.api.caller.ApiCaller.preApiCall("attachSignature", EzetapUIContext.getContext().getJSON("txnDetailresponse"), context);
				com.ezetap.android.api.caller.ApiCaller.callApi("attachSignature", EzetapUIContext.getContext().getJSON("txnDetailresponse"), context, 300);
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
					com.ezetap.android.api.caller.ApiCaller.postApiCall("void", apiResult, this);				
					ctx.forcePut("voidresponse", apiResult);
					sendResponseTOJS("void", apiResult);
					com.ezetap.android.api.caller.ApiCaller.preApiCall("txnHistory", EzetapUIContext.getContext().getJSON("voidresponse"), this);
					com.ezetap.android.api.caller.ApiCaller.callApi("txnHistory", EzetapUIContext.getContext().getJSON("voidresponse"), this, 105);
				} else {
					if(data != null ) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("void", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
						sendResponseTOJS("void", apiResult);
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
			
		if(requestCode == 105){
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
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA)){
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
		}	


		if(requestCode == 300){
			try {
				if(resultCode == 2001 || resultCode == RESULT_OK) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("attachSignature", apiResult, this);				
					ctx.forcePut("attachSignatureresponse", apiResult);
					sendResponseTOJS("attachSignature", apiResult);
					com.ezetap.android.api.caller.ApiCaller.preApiCall("txnHistory", EzetapUIContext.getContext().getJSON("attachSignatureresponse"), this);
					com.ezetap.android.api.caller.ApiCaller.callApi("txnHistory", EzetapUIContext.getContext().getJSON("attachSignatureresponse"), this, 305);
				} else {
					if(data != null ) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("attachSignature", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
						sendResponseTOJS("attachSignature", apiResult);
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
			
		if(requestCode == 305){
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
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA)){
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
		}	

	}
	
	private String compute_lbl_txn_detail_creation_time() {
		try {
			return com.ezetap.android.utils.UIUtils.format(EzetapUtils.formatDate((String)ctx.get("txnDetailresponse.chargeSlipDate")));
		}catch (Exception e) {
			return "";
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