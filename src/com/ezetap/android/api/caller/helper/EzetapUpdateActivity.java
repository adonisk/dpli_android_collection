package com.ezetap.android.api.caller.helper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ezetap.utils.EzeConstants;

public class EzetapUpdateActivity extends Activity {

	private ArrayList<String> updateJSON = null;
	int currentIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		updateJSON = intent.getStringArrayListExtra(EzeConstants.KEY_UPDATE_JSON);
		if(updateJSON.size()>0 ) {
			Intent updateIntent = new Intent(this, EzetapUpdateDetailActivity.class);
			updateIntent.putExtra(EzeConstants.KEY_UPDATE_JSON, updateJSON.get(0));
			updateIntent.putExtra("currentIndex", currentIndex);
			startActivityForResult(updateIntent, 100);
		} else {
			Intent intent1 = new Intent();
			setResult(2001, intent1);
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 100) {
			if(resultCode == RESULT_OK) {
				if(currentIndex != -1) {
					++currentIndex;
					if(currentIndex < updateJSON.size()) {
						Intent updateIntent = new Intent(this, EzetapUpdateDetailActivity.class);
						updateIntent.putExtra(EzeConstants.KEY_UPDATE_JSON, updateJSON.get(currentIndex));
						updateIntent.putExtra("currentIndex", currentIndex);
						startActivityForResult(updateIntent, 100);
					} else {
						Intent intent = new Intent();
						setResult(2001, intent);
						finish();
					}
				}
			} else {
				com.ezetap.android.utils.UIUtils.showToast("str_update_canceled", EzetapUpdateActivity.this);
				Intent intent = new Intent();
				setResult(3001, intent);
				finish();
			}
		}
	}
}
