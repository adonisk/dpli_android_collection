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

public class TxnSummaryRow extends BaseAdapter {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	
	private int id;
	private LayoutInflater inflater;
	private Summary parent;
	private Vibrator mVibrator;
	public TxnSummaryRow(Summary list,int rowListItem, Vibrator mVibrator) {
		this.parent = list;
		this.inflater = LayoutInflater.from(list);
		this.id = rowListItem;
		this.mVibrator = mVibrator;
	}

	@Override
	public Object getItem(int position) {
		 return ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.summary[]", position));
		
	}

	@Override
	public int getCount() {
		try{
			return ((JSONArray)ctx.get("txnHistoryresponse.summary")).length();
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
		TxnSummaryRowViewHolder viewHolder;
		 if (convertView == null) {
	            convertView = inflater.inflate(R.layout.row_txn_summary, null);
	            viewHolder = new TxnSummaryRowViewHolder();
	            viewHolder.lilbl_txn_summary_title = (TextView) convertView.findViewById(R.id.lilbl_txn_summary_title);
	            viewHolder.lilbl_txn_count = (TextView) convertView.findViewById(R.id.lilbl_txn_count);
	            viewHolder.lbl_txn_summary_currency_code = (TextView) convertView.findViewById(R.id.lbl_txn_summary_currency_code);
	            viewHolder.lilbl_summary_amount = (TextView) convertView.findViewById(R.id.lilbl_summary_amount);
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (TxnSummaryRowViewHolder) convertView.getTag();
	        }
		 
	     viewHolder.setTag(position);
    	viewHolder.lilbl_txn_summary_title.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.summary[].title", position)).toString());
    	((TextView) viewHolder.lilbl_txn_summary_title.findViewById(R.id.lilbl_txn_summary_title)).setTextColor(Color.parseColor(EzetapUtils.getColorForTxnSummaryStatus(((String)ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.summary[].title", position))))));
		 viewHolder.lilbl_txn_count.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.summary[].count", position), 1).toString());
		 viewHolder.lbl_txn_summary_currency_code.setText(ctx.get(EzetapUtils.setArrayIndexPosition("loginresponse.currencyCode", position), 1.0).toString());
		 viewHolder.lilbl_summary_amount.setText(ctx.get(EzetapUtils.setArrayIndexPosition("txnHistoryresponse.summary[].amount", position), 1).toString());
		 return convertView;
	}
	
	static class TxnSummaryRowViewHolder {
				TextView lilbl_txn_summary_title;
				TextView lilbl_txn_count;
				TextView lbl_txn_summary_currency_code;
				TextView lilbl_summary_amount;
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
