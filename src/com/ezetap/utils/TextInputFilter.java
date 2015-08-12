package com.ezetap.utils;

import java.util.Calendar;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextInputFilter implements TextWatcher {
	
	private static final String DEBUG_TAG = "TextWatcher";
	final EditText text;
	private String current = "";
    private String ddmmyyyy = "DDMMYYYY";

	
	public static enum FILTER_TYPE{
			FILTER_AMOUNT,
			FILTER_DATE,
	};
	private final FILTER_TYPE type;
	
	public TextInputFilter(EditText editText, FILTER_TYPE aType) {
		this.text = editText;
		this.type = aType;
		text.setSelection(text.getText().toString().length());
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(type == FILTER_TYPE.FILTER_AMOUNT) {
			if (s.length() > 0 ) {
				String input = s.toString();
				String numbersOnly = keepNumbersOnly(input);
				String code = formatNumbersAsCode(numbersOnly);
				
				int currentSelection = text.getSelectionStart();
				
				
				text.removeTextChangedListener(this);
				text.setText(code);
				
				// You could also remember the previous position of the cursor
				text.setSelection(code.length());
				
				text.addTextChangedListener(this);
				
			} else {
			}
		} else if(type == FILTER_TYPE.FILTER_DATE){
			try{
			if (!s.toString().equals(current)) {
	            String clean = s.toString().replaceAll("[^\\d.]", "");
	            String cleanC = current.replaceAll("[^\\d.]", "");

	            int cl = clean.length();
	            int sel = cl;
	            for (int i = 2; i <= cl && i < 6; i += 2) {
	                sel++;
	            }
	            //Fix for pressing delete next to a forward slash
	            if (clean.equals(cleanC)) sel--;

	            if (clean.length() < 8){
	               clean = clean + ddmmyyyy.substring(clean.length());
	            }else{
	               //This part makes sure that when we finish entering numbers
	               //the date is correct, fixing it otherways
	               int day  = Integer.parseInt(clean.substring(0,2));
	               int mon  = Integer.parseInt(clean.substring(2,4));
	               int year = Integer.parseInt(clean.substring(4,8));
	               if(mon > 12) mon = 12;
	               day = (day > 31)? 31:day;
	               clean = String.format(java.util.Locale.ENGLISH, "%02d%02d%04d",day, mon, year);
	            }

	            clean = String.format(java.util.Locale.ENGLISH, "%s/%s/%s", clean.substring(0, 2),
	                clean.substring(2, 4),
	                clean.substring(4, 8));
	            current = clean;
	            text.removeTextChangedListener(this);
	            text.setText(current);
	            if(sel == 0)
	            	sel = cl;
	            text.setSelection(sel < current.length() ? sel : current.length());
	            text.addTextChangedListener(this);
	        }
			} catch(Exception e){
			}
		}

	}

	
	private String keepNumbersOnly(CharSequence s) {
        return s.toString().replaceAll("[^0-9]", ""); // Should of course be more robust
    }
	
	
	private String formatNumbersAsCode(CharSequence s) {
        String tmp = "";
        
        if(type == FILTER_TYPE.FILTER_AMOUNT) {
        
        	StringBuffer sb = new StringBuffer("000"+s);
        	// remove leading zeros
        	while (sb.length() > 3) {
        		if(sb.charAt(0) == '0') {
        			sb.deleteCharAt(0);
        		} else {
        			break;
        		}
        	}
        	sb.insert(sb.length() - 2, '.');
        	tmp = sb.toString();
        }
        if(tmp.equals("0.00")) {
        	tmp = "";
        }
        
        return tmp;
    }
	
	
}
