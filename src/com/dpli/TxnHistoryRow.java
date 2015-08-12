package com.dpli;

import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Vibrator;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Paint;

import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.*;
import com.ezetap.utils.*;

public class TxnHistoryRow extends BaseAdapter {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	
	private int id;
	private LayoutInflater inflater;
	private TransactionHistory parent;
	private Vibrator mVibrator;
	public TxnHistoryRow(TransactionHistory list,int rowListItem, Vibrator mVibrator) {
		this.parent = list;
		this.inflater = LayoutInflater.from(list);
		this.id = rowListItem;
		this.mVibrator = mVibrator;
	}

	@Override
	public Object getItem(int position) {
		 return ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[]", position));
		
	}

	@Override
	public int getCount() {
		try{
			return ((JSONArray)ctx.get("txnHistoryresponse.txns")).length();
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TxnHistoryRowViewHolder viewHolder;
		 if (convertView == null) {
	            convertView = inflater.inflate(R.layout.row_txn_history, null);
	            viewHolder = new TxnHistoryRowViewHolder();
	            viewHolder.lilbl_txn_id = (TextView) convertView.findViewById(R.id.lilbl_txn_id);
	            viewHolder.lbl_txn_price_currency_code = (TextView) convertView.findViewById(R.id.lbl_txn_price_currency_code);
	            viewHolder.lilbl_txn_amount = (TextView) convertView.findViewById(R.id.lilbl_txn_amount);
	            viewHolder.lilbl_txn_status = (TextView) convertView.findViewById(R.id.lilbl_txn_status);
	            viewHolder.lbl_txn_price_currency_code_cashback = (TextView) convertView.findViewById(R.id.lbl_txn_price_currency_code_cashback);
	            viewHolder.lilbl_txn_amountCashback = (TextView) convertView.findViewById(R.id.lilbl_txn_amountCashback);
	            viewHolder.lilbl_shipment_id_str = (TextView) convertView.findViewById(R.id.lilbl_shipment_id_str);
	            viewHolder.lilbl_shipment_id_str_1 = (TextView) convertView.findViewById(R.id.lilbl_shipment_id_str_1);
	            viewHolder.lilbl_shipment_id_str_old = (TextView) convertView.findViewById(R.id.lilbl_shipment_id_str_old);
	            viewHolder.lilbl_shipment_id = (TextView) convertView.findViewById(R.id.lilbl_shipment_id);
	            viewHolder.lilbl_txn_history_payee_name = (TextView) convertView.findViewById(R.id.lilbl_txn_history_payee_name);
	            viewHolder.lilbl_txn_history_mobile = (TextView) convertView.findViewById(R.id.lilbl_txn_history_mobile);
	            viewHolder.lilbl_txn_history_payment_type = (ImageView) convertView.findViewById(R.id.lilbl_txn_history_payment_type);
	            viewHolder.lilbl_txn_history_card_type = (ImageView) convertView.findViewById(R.id.lilbl_txn_history_card_type);
	            viewHolder.lilbl_txn_history_card_prefix = (TextView) convertView.findViewById(R.id.lilbl_txn_history_card_prefix);
	            viewHolder.lilbl_txn_history_card_last_four = (TextView) convertView.findViewById(R.id.lilbl_txn_history_card_last_four);
	            viewHolder.lbl_txn_detail_card_txn_type_desc = (TextView) convertView.findViewById(R.id.lbl_txn_detail_card_txn_type_desc);
	            viewHolder.lilbl_txn_history_emi = (TextView) convertView.findViewById(R.id.lilbl_txn_history_emi);
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (TxnHistoryRowViewHolder) convertView.getTag();
	        }
		 
	     viewHolder.setTag(position);
    	viewHolder.lilbl_txn_id.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].txnId", position)).toString());
    	viewHolder.lbl_txn_price_currency_code.setText(ctx.get(EzetapUtils.setArrayIndexPosition("loginresponse.currencyCode", position)).toString());
		 viewHolder.lilbl_txn_amount.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].amount", position), 1.0).toString());
    	viewHolder.lilbl_txn_status.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].status", position)).toString());
    	((TextView) viewHolder.lilbl_txn_status.findViewById(R.id.lilbl_txn_status)).setTextColor(Color.parseColor(EzetapUtils.getColorForTxnStatus(((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].status", position))))));
    	viewHolder.lbl_txn_price_currency_code_cashback.setText(ctx.get(EzetapUtils.setArrayIndexPosition("loginresponse.currencyCode", position)).toString());
		 viewHolder.lilbl_txn_amountCashback.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].amountCashBack", position), 1.0).toString());
    	viewHolder.lilbl_shipment_id_str.setText(ctx.get(EzetapUtils.setArrayIndexPosition("loginresponse.setting.orderNumberLabel", position)).toString());
    	if(!StringUtils.hasText((String)ctx.get("loginresponse.setting.orderNumberLabel")))
    	viewHolder.lilbl_shipment_id_str.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_shipment_id_str.setVisibility(View.VISIBLE);
    	if(!StringUtils.hasText((String)ctx.get("loginresponse.setting.orderNumberLabel")))
    	viewHolder.lilbl_shipment_id_str_1.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_shipment_id_str_1.setVisibility(View.VISIBLE);
    	if(StringUtils.hasText((String)ctx.get("loginresponse.setting.orderNumberLabel")))
    	viewHolder.lilbl_shipment_id_str_old.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_shipment_id_str_old.setVisibility(View.VISIBLE);
    	viewHolder.lilbl_shipment_id.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].externalRefNumber", position)).toString());
    	viewHolder.lilbl_txn_history_payee_name.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].customerName", position)).toString());
    	if(!StringUtils.hasText((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].customerName", position))))
    	viewHolder.lilbl_txn_history_payee_name.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_txn_history_payee_name.setVisibility(View.VISIBLE);
    	viewHolder.lilbl_txn_history_mobile.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].customerMobile", position)).toString());
    	if(!StringUtils.hasText((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].customerMobile", position))))
    	viewHolder.lilbl_txn_history_mobile.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_txn_history_mobile.setVisibility(View.VISIBLE);
    	SetImageUtils.setImageForPaymentType(this.parent, viewHolder.lilbl_txn_history_payment_type, (String) ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].paymentMode", position)));
    	if(((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].paymentMode", position))).equals("CARD"))
    	viewHolder.lilbl_txn_history_payment_type.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_txn_history_payment_type.setVisibility(View.VISIBLE);
    	SetImageUtils.setImageForCardType(this.parent, viewHolder.lilbl_txn_history_card_type, (String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].cardType", position)));
    	if(!((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].paymentMode", position))).equals("CARD"))
    	viewHolder.lilbl_txn_history_card_type.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_txn_history_card_type.setVisibility(View.VISIBLE);
    	if(!((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].paymentMode", position))).equals("CARD"))
    	viewHolder.lilbl_txn_history_card_prefix.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_txn_history_card_prefix.setVisibility(View.VISIBLE);
    	viewHolder.lilbl_txn_history_card_last_four.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].cardLastFourDigit", position)).toString());
    	if(!((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].paymentMode", position))).equals("CARD"))
    	viewHolder.lilbl_txn_history_card_last_four.setVisibility(View.GONE);
    	else
    	viewHolder.lilbl_txn_history_card_last_four.setVisibility(View.VISIBLE);
    	viewHolder.lbl_txn_detail_card_txn_type_desc.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].cardTxnTypeDesc", position)).toString());
    	if(!((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].paymentMode", position))).equals("CARD"))
    	viewHolder.lbl_txn_detail_card_txn_type_desc.setVisibility(View.GONE);
    	else
    	viewHolder.lbl_txn_detail_card_txn_type_desc.setVisibility(View.VISIBLE);
    	viewHolder.lilbl_txn_history_emi.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].emiTerm", position)).toString());
		if(!(ctx.get((String)EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].amountCashBack", position)).getClass().equals(Integer.class)    && ((Integer)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].amountCashBack", position)))>0))
		convertView.findViewById(R.id.grp_txn_history_list_item_cashback).setVisibility(View.GONE);
		else
		convertView.findViewById(R.id.grp_txn_history_list_item_cashback).setVisibility(View.VISIBLE);
		if(!StringUtils.hasText((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.txns[].emiTerm", position))))
		convertView.findViewById(R.id.grp_txn_history_emi).setVisibility(View.GONE);
		else
		convertView.findViewById(R.id.grp_txn_history_emi).setVisibility(View.VISIBLE);
		 return convertView;
	}
	
	static class TxnHistoryRowViewHolder {
				TextView lilbl_txn_id;
				TextView lbl_txn_price_currency_code;
				TextView lilbl_txn_amount;
				TextView lilbl_txn_status;
				TextView lbl_txn_price_currency_code_cashback;
				TextView lilbl_txn_amountCashback;
				TextView lilbl_shipment_id_str;
				TextView lilbl_shipment_id_str_1;
				TextView lilbl_shipment_id_str_old;
				TextView lilbl_shipment_id;
				TextView lilbl_txn_history_payee_name;
				TextView lilbl_txn_history_mobile;
			ImageView lilbl_txn_history_payment_type;
			ImageView lilbl_txn_history_card_type;
				TextView lilbl_txn_history_card_prefix;
				TextView lilbl_txn_history_card_last_four;
				TextView lbl_txn_detail_card_txn_type_desc;
				TextView lilbl_txn_history_emi;
		Object tag;
		public Object getTag() {
			return tag;
		}
		public void setTag(Object tag) {
			this.tag = tag;
		} 
	}
	
	@Override
	public void notifyDataSetChanged (){
		super.notifyDataSetChanged();
		
	}
	
	
}
