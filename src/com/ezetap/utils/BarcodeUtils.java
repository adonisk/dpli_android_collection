package com.ezetap.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class BarcodeUtils {

	public static void handleBarcodeScan(Activity callingActivity, int requestCode, int resultCode, Intent data, int txt_amount,int txt_bill_number){
		
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		 
		if (scanResult != null) {
			View v = callingActivity.findViewById(txt_bill_number);
			View v1 = callingActivity.findViewById(txt_amount);
			if(v instanceof EditText && v1 instanceof EditText) {
				EditText curr = (EditText) callingActivity.getCurrentFocus();
				if(curr.getId() == txt_amount) {
					String contents = scanResult.getContents();
					contents = contents.toLowerCase();
					if(contents.length() > 3) {
						contents = contents.substring(3);
						int indexOfDot = contents.indexOf(".");
						if(indexOfDot >= 0) {
						 
							try {
								Double amount = Double.parseDouble(contents);
								curr.setText(""+amount);
							} catch (Exception e) {
							}
							
						} else {
							try {
								Double amount = Double.parseDouble(contents)/100.0;
								curr.setText(""+amount);
							} catch (Exception e) {
							}
							
						}
						
					}
				} else if(curr.getId() == txt_bill_number) {
					((EditText)v).setText(scanResult.getContents());
				}
			}
	    
	    }
		
		return;
	}
	
public static void handleBarcodeScan(Activity callingActivity, int requestCode, int resultCode, Intent data, int txt_id){
		
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		 
		if (scanResult != null) {
			View v = callingActivity.findViewById(txt_id);
			if(v instanceof EditText) {
					String contents = scanResult.getContents();
					contents = contents.toLowerCase();
					if(contents.length() > 3) {
						int indexOfDot = contents.indexOf(".");
						if(indexOfDot >= 0) {
							contents = contents.substring(3);
							try {
								Double amount = Double.parseDouble(contents)/100;
								((EditText) v).setText(""+amount);
							} catch (Exception e) {
							}
							
						} else {
							try {
								((EditText) v).setText(""+contents);
							} catch (Exception e) {
							}
							
						}
						
					}
				}
			}
		return;
	}
}
