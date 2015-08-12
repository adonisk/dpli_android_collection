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

public class BankRow extends BaseAdapter {

	private EzetapUIContext ctx = EzetapUIContext.getContext();
	
	private int id;
	private LayoutInflater inflater;
	private BankSelection parent;
	private Vibrator mVibrator;
	public BankRow(BankSelection list,int rowListItem, Vibrator mVibrator) {
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
		BankRowViewHolder viewHolder;
		 if (convertView == null) {
	            convertView = inflater.inflate(R.layout.row_bank, null);
	            viewHolder = new BankRowViewHolder();
	            viewHolder.lbl_li_bs_bank_name = (TextView) convertView.findViewById(R.id.lbl_li_bs_bank_name);
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (BankRowViewHolder) convertView.getTag();
	        }
		 
	     viewHolder.setTag(position);
    	viewHolder.lbl_li_bs_bank_name.setText(ctx.get(EzetapUtils.setArrayIndexPosition("loginresponse.setting.chequePaymentBankNames[].codeDescription", position)).toString());
		 return convertView;
	}
	
	static class BankRowViewHolder {
				TextView lbl_li_bs_bank_name;
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
