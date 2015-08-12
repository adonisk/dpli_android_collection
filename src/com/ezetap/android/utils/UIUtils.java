

package com.ezetap.android.utils;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ezetap.android.api.caller.helper.ApiHelperBase;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.utils.MyTextView;

public class UIUtils {
	private static NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ENGLISH); 
	
	public static String format(String d){
		return d;
	}
	
	public static String format(Object d) {
		return formatter.format(d);
	}
	
	public static String format(double d) {
		return formatter.format(d);
	}
	
	public static String unFormat(String number) {
		return number.replace(",", "");
	}

	public static void setListAdapterToListActivity(Activity activity, BaseAdapter adapter){
		((ListActivity) activity).setListAdapter(adapter);
	} 
	
	public static void enableOrHideControl(Activity activity, int viewId, boolean disable, boolean hide) {
		View v  = activity.findViewById(viewId);
		if (hide) {
			v.setVisibility(View.GONE);
			return;
		} else {
			v.setVisibility(View.VISIBLE);
		}
		if (disable) {
			v.setEnabled(false);
		} else {
			v.setEnabled(true);
		}
	}
	
	public static void enableOrDisableControl(Activity activity, int viewId, boolean disable) {
		View v  = activity.findViewById(viewId);
		if (disable) {
			v.setEnabled(false);
		} else {
			v.setEnabled(true);
		}
	}
	
	public static void setBackgroundImage(Activity activity, int viewId, int drawableId){
		activity.findViewById(viewId).setBackgroundResource(drawableId);
	}
	
	public static void moveAppToBackground(Activity activity){
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(setIntent);
	}
	
	public static void performExit(final Activity activity){
		AlertDialog.Builder exitDialog = new AlertDialog.Builder(activity);
		exitDialog.setMessage(getResourceString("msg_exit_application", activity));
		exitDialog.setPositiveButton(getResourceString("btn_yes", activity),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						moveAppToBackground(activity);
					}
				});
		
		exitDialog.setNegativeButton(getResourceString("btn_no", activity), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						
					}
				});
		exitDialog.show();
	}
	
	public static void setPendingOffline(JSONObject result){
		try {
			if(result.has("offlineCount")) {
				JSONObject o = new JSONObject();
				o.put("offlineCount", result.get("offlineCount"));
				
				EzetapUIContext.getContext().put("offlineCountData", o);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setFontColor(Activity activity, int viewId, String colorId){
		((TextView)activity.findViewById(viewId)).setTextColor(Color.parseColor(colorId));
	}
	
	public static void setText(Activity activity, int viewId, String text){
		((TextView)activity.findViewById(viewId)).setText(text);
	}

	public static void setButtonText(Activity activity, int viewId, String text){
		((Button)activity.findViewById(viewId)).setText(text);
	}

	public static void setButtonText(Activity activity, int viewId, int text){
		((Button)activity.findViewById(viewId)).setText(String.valueOf(text));
	}
	
	public static String getText(Activity activity, int viewId){
		String text = new String();
		TextView textView = (TextView)activity.findViewById(viewId);
		if(textView != null)
			text = textView.getText().toString();
		return text;
	}
	
	public static double getDoubleValue(Activity activity, int viewId){
		String text = new String();
		TextView textView = (TextView)activity.findViewById(viewId);
		if(textView != null)
			text = textView.getText().toString();
		double value = 0.0;
		try {
			value = Double.parseDouble(text);
		} catch(Exception e) {}
		return value;
	}
	
	public static int getTextLength(Activity activity, int viewId){
		int length = 0;
		TextView textView = (TextView)activity.findViewById(viewId);
		if(textView != null)
			length = textView.getText().length();
		return length;
	} 
	
	public static int getTextLength(TextView view){
		int length = 0;
		if(view != null)
			length = view.getText().length();
		return length;
	} 
	
	public static Double getAmount(Object o) {
		double amount = 0;
		try {
			JSONObject response = (JSONObject) o;
			if(response != null && response.has("response"))
				response = response.getJSONObject("response");
			String amountLabel = null;
			if(response != null && response.has("uimeta"))
				response = response.getJSONObject("uimeta");
			if(response != null && !response.isNull("_amountField"))
				amountLabel = response.getString("_amountField");
			if(amountLabel != null && (response.getString(amountLabel)).length() != 0)
				amount = response.getDouble(amountLabel);
			if(amount < 0)
				amount = 0;
		} catch (Exception e) { }
		return amount;
	}
	
	public static void searchJSON(Object o, String namespace) {
		searchJSON(o, namespace, 0, null);
	}
	
	public static void searchJSON(Object o, String namespace, int textID, Activity activity) {
		try {
			JSONArray response = (JSONArray) o;
			EzetapUIContext ctx = EzetapUIContext.getContext();
			String text = new String();
			if(activity != null) {
				EditText textView = (EditText)activity.findViewById(textID);
				if(textView != null)
					text = textView.getText().toString();
				if(text.length() == 0) {
					ctx.put(namespace, response);
					return;
				}
			}
			if(response != null){ // search here
				JSONArray result = new JSONArray();
				for(int i = 0; i < response.length(); i++) {
					JSONObject json = response.getJSONObject(i);
					String mobile = json.getString("mobile");
					String ref = json.getString("__ref");
					String name = json.getString("name");
					String address = json.getString("address");	
					if(stringcontains(mobile, text) || stringcontains(ref, text) ||
					   stringcontains(name, text) || stringcontains(address, text)) {
						result.put(json);
					}
				}
				ctx.put(namespace, result);
			}
		} catch (Exception e) {		}
	}
	
	public static boolean stringcontains(String str1, String str2) {
		if(str1 == null || str2 == null) return false;
		str1 = str1.toLowerCase(Locale.ENGLISH);
		str2 = str2.toLowerCase(Locale.ENGLISH);
		return str1.contains(str2);
	}
	
	public static void setTextLength(TextView view, String maxlength){
		if(view != null) {
			try {
				int max = Integer.parseInt(maxlength);
				view.setFilters(new android.text.InputFilter[] { new android.text.InputFilter.LengthFilter(max) });
			} catch(Exception e) {}
		}
	}
	
	public static void setTextInputType(TextView view, String type){
		if(view != null) {
			if(type.equalsIgnoreCase("phone") || type.equalsIgnoreCase("number"))
				view.setInputType(InputType.TYPE_CLASS_PHONE);
			else if(type.equalsIgnoreCase("text"))
				view.setInputType(InputType.TYPE_CLASS_TEXT);
			else
				view.setInputType(InputType.TYPE_CLASS_TEXT);
		}
	}
	
	public static void setTextLength(Activity activity, int viewID, String maxlength){
		TextView view = (TextView)activity.findViewById(viewID);
		if(view != null) {
			try {
				int max = Integer.parseInt(maxlength);
				view.setFilters(new android.text.InputFilter[] { new android.text.InputFilter.LengthFilter(max) });
			} catch(Exception e) {}
		}
	}
	
	public static void setTextInputType(Activity activity, int viewID, String type){ 
		TextView view = (TextView)activity.findViewById(viewID);
		if(view != null) {
			if(type.equalsIgnoreCase("phone") || type.equalsIgnoreCase("number"))
				view.setInputType(InputType.TYPE_CLASS_PHONE);
			else if(type.equalsIgnoreCase("text"))
				view.setInputType(InputType.TYPE_CLASS_TEXT);
			else
				view.setInputType(InputType.TYPE_CLASS_TEXT);
		}
	}
	public static String getNamespace(String json, String key) {
		EzetapUIContext ctx = EzetapUIContext.getContext();
		return json.concat((String)ctx.get(key));
	}
	
	public static boolean isTextValid(String key_options, String key_maxLen, String key_namespace, String key_field) {
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			JSONArray array = (JSONArray) ctx.get(key_options);
			if(array == null) return false;
			for(int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				if(json != null) {
					String text = ctx.get(key_namespace.concat(json.getString(key_field))).toString();
					int maxlen = Integer.parseInt(json.getString(key_maxLen));
					if(text.length() < maxlen)
						return false;
				}
			}
		} catch(JSONException e) { return false; }
		return true;
	}
	
	public static boolean isTextValid(String key_options, String key_maxLen, String key_namespace) {
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			JSONArray array = (JSONArray) ctx.get(key_options);
			if(array == null) return false;
			for(int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				if(json != null) {
					String text = ctx.get(key_namespace).toString();
					int maxlen = Integer.parseInt(json.getString(key_maxLen));
					if(text.length() < maxlen)
						return false;
				}
			}
		} catch(JSONException e) { return false; }
		return true;
	}
	
	public static void disableFullRefresh() {
		ApiHelperBase.isFullRefresh = false;
	}
	
	public static void enableFullRefresh() {
		ApiHelperBase.isFullRefresh = true;
	}
	
	public static void removeJSON(String namespace) {
		EzetapUIContext ctx = EzetapUIContext.getContext();
		ctx.removeJSON(namespace);
	}
	
	public static void copyJSON(String src_namespace, String dest_namespace) {
		EzetapUIContext ctx = EzetapUIContext.getContext();
		if(ctx.get(dest_namespace) == null) {
			ctx.put(dest_namespace, ctx.getJSON(src_namespace));
		} else {
			JSONObject json = ctx.getJSON(dest_namespace);
			if(!json.has("amount")) {
				ctx.removeJSON(dest_namespace);
				ctx.put(dest_namespace, ctx.getJSON(src_namespace));
			}
		}
	}
	
	public static void copyJSON(Object object, String namespace) {
		if(object != null) {
			JSONObject json = (JSONObject) object;
			if(json != null) {
				EzetapUIContext.getContext().removeJSON(namespace);
				EzetapUIContext.getContext().put(namespace, json);
			}
		}
	}
	
	/**
	 * This function creates a copy of the JSON Object. This ensures that immutable objects can also be modified independently
	 * @param Object object
	 * @param String namespace
	 */
	public static JSONObject createCopyJSON(JSONObject object) {
		if(object == null)
			return null;
		JSONObject newJSON = new JSONObject();
		try {
			Iterator itr = object.keys();
			while(itr.hasNext()) {
				String key = (String)itr.next();
				newJSON.put(key, object.get(key));
			}
		} catch (JSONException e) {
		}
		return newJSON;
	}
	
	public static void checkPendingDeliveries(Activity activity, Integer count, String message) {
		if(count == 0) {
			showToastMessage(message, activity);
			activity.finish();
		}
	}	
	
	public static int getResourceId(String id, Activity activity) {
		if(activity == null)
			return 0;
		return activity.getResources().getIdentifier(id, "string", activity.getPackageName());
	}
	
	public static int getResourceLayout(String id, Activity activity) {
		if(activity == null)
			return 0;
		return activity.getResources().getIdentifier(id, "layout", activity.getPackageName());
	}
	
	public static String getResourceString(String id, Activity activity) {
		if(activity == null)
			return id;
		return activity.getResources().getString(getResourceId(id, activity));
	}
	
	public static void showToast(String id, final Activity activity) {
		String msg = getResourceString(id, activity);
		showToastMessage(msg, activity);
	}
	
	public static void showToastMessage(final String msg, final Activity activity) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				LayoutInflater inflater = activity.getLayoutInflater();
				View layout = inflater.inflate(getResourceLayout("toast", activity), 
						(ViewGroup) activity.findViewById(getResourceLayout("toast_layout_root", activity)));
				MyTextView text = (MyTextView) layout.findViewById(activity.getResources().getIdentifier("toast_msg", 
						"id", activity.getPackageName()));
				text.setText(msg);
				android.widget.Toast toast = new android.widget.Toast(activity.getApplicationContext());
				toast.setDuration(android.widget.Toast.LENGTH_SHORT);
				toast.setView(layout);
				toast.show();
			}
		});
	}
	
	public static void showAlertDialog(final Activity activity, final String message, final String title){
		final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
		dialog.setMessage(message);
		dialog.setTitle(title);
		dialog.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
					}
				});
		dialog.show();
	}
}