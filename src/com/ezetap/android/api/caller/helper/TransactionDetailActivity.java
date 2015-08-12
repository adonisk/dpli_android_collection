package com.ezetap.android.api.caller.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ezetap.utils.EzeConstants;

public class TransactionDetailActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent =  getIntent();
		Intent responseIntent = new Intent();
		if(intent.getStringExtra(EzeConstants.KEY_ACTION).equals("txnDetail")){
			
			String itemDetail = intent.getStringExtra("TXN_JSON").toString();
			if(itemDetail != null) {
				responseIntent.putExtra(EzeConstants.KEY_RESPONSE_DATA, itemDetail);
				setResult(EzeConstants.RESULT_SUCCESS, responseIntent);
			} else {
				responseIntent.putExtra(EzeConstants.KEY_ERROR_CODE, "NO_DATA");
				responseIntent.putExtra(EzeConstants.KEY_ERROR_MESSAGE, "No data found");
				setResult(EzeConstants.RESULT_FAILED, responseIntent);
			}
		}
			
		setResult(2001, responseIntent);
		finish();
		
	}
}
