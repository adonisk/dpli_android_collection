package com.dpli;

import com.ezetap.android.api.caller.ApiCaller;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.utils.EzeConstants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EzetapBaseActivity extends Activity {
	private EzetapUIContext ctx = EzetapUIContext.getContext();
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(com.dpli.R.menu.menu_basic_app, menu);
		return true;
	}
	 
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	    {
	    
		 String title = item.getTitle().toString();
		 boolean retVal = false;
		 if(title.equals("About"))
		 {
				Intent intent = new Intent(this, com.dpli.About.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
		}
		 if(title.equals("Logout"))
		 {
				com.ezetap.android.api.caller.ApiCaller.preApiCall("logout", null, this);
				com.ezetap.android.api.caller.ApiCaller.callApi("logout", null, this, 2);
		}
		 return retVal;
	    }

	 public void onBackPressed(){
		 finish();
		 super.onBackPressed();
	 }
	 
	 public  void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == 2){
			try {
				if(resultCode == 2001) {
					org.json.JSONObject apiResult = null;
					if(data != null && data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
						apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					com.ezetap.android.api.caller.ApiCaller.postApiCall("logout", apiResult, this);				
					ctx.forcePut("logoutresponse", apiResult);
					Intent intent = new Intent(this, com.dpli.LogintoDPLI.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					if(data != null) {
						org.json.JSONObject apiResult = null;
						if(data.hasExtra(EzeConstants.KEY_RESPONSE_DATA))
							apiResult = new org.json.JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
						com.ezetap.android.api.caller.ApiCaller.onApiError("logout", apiResult, this);
						com.ezetap.android.utils.EzetapUtils.showError(data , this);
					}
				}
			} catch(org.json.JSONException e) {
				e.printStackTrace();
			}
		}

	}
		@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onPause() {
		super.onPause();
		writeToFile();
	}

	@Override
	public void onResume() {
		super.onResume();
		fetchFromFile();
	}
	
	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		fetchFromFile();
	}

	private void writeToFile() {
		FileOutputStream fos;
		try {
			fos = openFileOutput(EzetapConstDef.FILENAME, Context.MODE_PRIVATE);
			EzetapUIContext.getContext().serialize(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fetchFromFile() {
		try {
			FileInputStream fis = openFileInput(EzetapConstDef.FILENAME);
			EzetapUIContext.getContext().deserialize(fis);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
