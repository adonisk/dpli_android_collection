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

public class NoRow extends BaseAdapter {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	
	private int id;
	private LayoutInflater inflater;
	private ChequeDetails parent;
	private Vibrator mVibrator;
	public NoRow(ChequeDetails list,int rowListItem, Vibrator mVibrator) {
		this.parent = list;
		this.inflater = LayoutInflater.from(list);
		this.id = rowListItem;
		this.mVibrator = mVibrator;
	}

	@Override
	public Object getItem(int position) {
		 return ctx.get(EzetapUtils.setArrayIndexPosition("loginresponse.setting.chequePaymentBankNames[]", position));
		
	}

	@Override
	public int getCount() {
		try{
			return ((JSONArray)ctx.get("loginresponse.setting.chequePaymentBankNames")).length();
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
		NoRowViewHolder viewHolder;
		 if (convertView == null) {
	            convertView = inflater.inflate(R.layout.lst_item_bank_row, null);
	            viewHolder = new NoRowViewHolder();
	            viewHolder.lbl_bank_row = (TextView) convertView.findViewById(R.id.lbl_bank_row);
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (NoRowViewHolder) convertView.getTag();
	        }
		 
	     viewHolder.setTag(position);
    	viewHolder.lbl_bank_row.setText(ctx.get(EzetapUtils.setArrayIndexPosition("loginresponse.setting.chequePaymentBankNames[].code", position)).toString());
		 return convertView;
	}
	
	static class NoRowViewHolder {
				TextView lbl_bank_row;
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
