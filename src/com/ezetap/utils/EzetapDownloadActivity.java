package com.ezetap.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class EzetapDownloadActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        JSONObject response = new JSONObject();
        intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, response.toString());
        setResult(3001, intent);
        finish();
	}
}
