package com.ezetap.android.utils;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ezetap.android.api.caller.helper.InitializeDongleApihelper;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.JUtils;
import com.ezetap.android.utils.UIUtils;

public class EzetapUtils {
	
	private static ConnectivityManager connectivityMgr = null;

	private static final String DEBUG_TAG = "EzetapUtils";
	
	
	public static String getEMILabel(int indexInJSON) {
		String label = "";
		try {
		JSONObject emiSettings =  new JSONObject(EzetapUIContext.getContext().get("loginresponse.setting.emiSettings").toString());
		
		if(emiSettings!=null && emiSettings.has("emiOptionLabels")) {
			JSONArray ops = emiSettings.getJSONArray("emiOptionLabels");
			if(ops != null && !(indexInJSON > ops.length())) {
				label = ops.getString(indexInJSON);
			}
		}
		} catch(JSONException e) {
			Log.v(DEBUG_TAG, e.getMessage());
			return label;
		}
		return label;
	}
	
	public static void setEMIButtonText(int indexInJSON, int id, Activity context) {
		try {
		String[] defaultValues = new String[]{"Pay In Full", "3 Month EMI", "6 Month EMI", "9 Month EMI"};
		
		Button b = (Button) context.findViewById(id);
		String text = defaultValues[indexInJSON];
		
		JSONObject emiSettings =  new JSONObject(EzetapUIContext.getContext().get("loginresponse.setting.emiSettings").toString());
		
		if(emiSettings!=null && emiSettings.has("emiOptionDisplayText")) {
			JSONArray ops = emiSettings.getJSONArray("emiOptionDisplayText");
			if(ops != null && !(indexInJSON > ops.length())) {
				text = ops.getString(indexInJSON);
			}
		}
		b.setText(text);
		} catch(JSONException e) {
			Log.v(DEBUG_TAG, e.getMessage());
		}
		
	}
	
	public static boolean getIsEMIOptionHidden(int indexInJSON) {
		boolean ret = false;
		
		try {
		JSONObject emiSettings =  new JSONObject(EzetapUIContext.getContext().get("loginresponse.setting.emiSettings").toString());
		
		if(emiSettings!=null && emiSettings.has("emiOptionsEnabled")) {
			JSONArray ops = emiSettings.getJSONArray("emiOptionsEnabled");
			if(ops != null && !(indexInJSON > ops.length())) {
				ret = ops.getBoolean(indexInJSON);
			}
			
		}
		} catch(JSONException e) {
			Log.v(DEBUG_TAG, e.getMessage());
			ret = false;
		} 
		//should return if it is HIDDEN, so NOT the return value as the json contains whether it is enabled or not.
		//enabled = true :: hidden = false
		//enabled = false :: hidden = true
		return !ret;
	}
	
	public static boolean getBooleanValue(Object o, boolean defaultVal) {
		if(o == null) return Boolean.valueOf(defaultVal);
		if(o.toString().trim().length() == 0) return Boolean.valueOf(defaultVal);
		if(o instanceof Boolean) return (Boolean)o;
		if(o instanceof String) return Boolean.parseBoolean((String)o);
		
		return Boolean.valueOf(defaultVal);
	}
	
	public static Integer getIntegerValue(Object o, String defaultVal) {
		if(o == null) return Integer.valueOf(defaultVal);
		if(o.toString().trim().length() == 0) return Integer.valueOf(defaultVal);
		if(o instanceof Integer) return (Integer)o;
		if(o instanceof String) return Integer.valueOf((String)o);
		
		return Integer.valueOf(defaultVal);
	}
	
	public static Double getDoubleValue(Object o, String defaultVal) {
		if(o == null) return Double.valueOf(defaultVal);
		if(o.toString().trim().length() == 0) return Double.valueOf(defaultVal);
		if(o instanceof Double) return (Double)o;
		if(o instanceof Integer) return (Double)o;
		if(o instanceof String) return Double.valueOf((String)o);
		
		return Double.valueOf(defaultVal);
	}
	
	public static String setArrayIndexPosition(String key, int position) {
		if(!key.contains("[") || !key.contains("]"))
			return key;
		String s1 = key.substring(0, key.lastIndexOf("[")+1);
		String s2 = key.substring(key.lastIndexOf("]"));
		String pos = Integer.toString(position);
		return s1.concat(pos).concat(s2);
	}

	public static String getArrayName(String arrNameFull) {
		return arrNameFull.substring(0, arrNameFull.indexOf("["));
	}

	public static String getArray(String arrNameFull) {
		return arrNameFull.substring(0, arrNameFull.indexOf("]")+1);
	}

	public static boolean setFullRefresh(){
		com.ezetap.android.app.properties.EzetapProperties.putProperty("IS_FULL_REFRESH", true);
		return true;
	}
	
	public static boolean resetFullRefresh(){
		com.ezetap.android.app.properties.EzetapProperties.putProperty("IS_FULL_REFRESH", false);
		return true;
	}
	
	public static void showError(Intent data, Activity context) {
		try {
			String msg = "";
			if (data == null) {
				UIUtils.showToast("msg_no_results", context);
			} else {
				if (data.hasExtra(EzeConstants.KEY_RESPONSE_DATA)) {
					JSONObject o = new JSONObject(
							data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA));
					if (o.has("success") && o.getBoolean("success") == false) {
						if (o.has("errorCode")) {
							msg = msg.concat(o.getString("errorCode")+"\n");
						}
						if (o.has("errorMessage")) {
							msg = msg.concat(o.getString("errorMessage"));
						}
					}else {
						if (o.has("errorCode")) {
							msg = msg.concat(o.getString("errorCode")+"\n");
						}
						if (o.has("errorMessage")) {
							msg = msg.concat(o.getString("errorMessage"));
						}						
					}
				}
			}
			if(msg != null && msg.trim().length() != 0)
				UIUtils.showToastMessage(msg, context);
			
		} catch (JSONException e) {
			UIUtils.showToastMessage("msg_invalid_data", context);
		}

	}
	
	public static void showSuccess(String identifier, Activity activity){
		UIUtils.showToast(identifier, activity);
	}
	
	
	public static boolean checkConnectivity(Activity context) {
		if(connectivityMgr == null) {
			connectivityMgr = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		if(connectivityMgr.getActiveNetworkInfo()!=null && connectivityMgr.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			UIUtils.showToast("network_not_connected", context);
			return false;
		}
	}
	
	@SuppressWarnings("finally")
	public static String getVersionName(Activity context) {
		String ver = "";
		if(context != null) {
			try {
				PackageInfo p = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName() , 0);
				if(p != null) {
					ver = p.versionName;
					if(com.ezetap.android.app.properties.EzetapProperties.genMode.equalsIgnoreCase("DEMO")) {
						ver = ver + " [Demo]";
					} else if(com.ezetap.android.app.properties.EzetapProperties.genMode.equalsIgnoreCase("PREPROD")) {
						ver = ver + " [PreProd]";
					} else if(com.ezetap.android.app.properties.EzetapProperties.genMode.equalsIgnoreCase("OTHER")) {
						ver = ver + " [Debug]";
					} else if(com.ezetap.android.app.properties.EzetapProperties.genMode.equalsIgnoreCase("MOCK")) {
						ver = ver + " [Mock]";
					}
				}
			} catch (NameNotFoundException e) {
				UIUtils.showToastMessage("Unable to retrieve version information.", context);
			}finally {
				return ver;
			}
			
		}
		return ver;
	}
	
	@SuppressWarnings("finally")
	public static int getVersionCode(Activity context) {
		int ver = 0;
		if(context != null) {
			try {
				PackageInfo p = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0);
				if(p != null) ver = p.versionCode;
			} catch (NameNotFoundException e) {
				UIUtils.showToastMessage("Unable to retrieve version information.", context);
			} finally {
				return ver;
			}
		}
		return ver;
	}
	
	@SuppressWarnings("finally")
	public static String getAppName(Activity context) {
		String app = "";
		if(context != null) {
			try {
				PackageInfo p = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName() , 0);
				if(p != null) app = p.applicationInfo.loadLabel(context.getApplicationContext().getPackageManager()).toString();
			} catch (NameNotFoundException e) {
				UIUtils.showToastMessage("Unable to retrieve application information.", context);
			}finally {
				return app;
			}
			
		}
		return app;
	}
	
	public static String getEMIOptionStr() {
		String ret = "EMI Options not configured correctly";
		try {

//			JSONObject emiSettings = (JSONObject) EzetapUIContext.getContext().get("loginresponse.setting.emiSettings");
			if(EzetapUIContext.getContext().get("loginresponse.setting.emiSettings") == null) return ret;
			
			JSONObject emiSettings =  new JSONObject(EzetapUIContext.getContext().get("loginresponse.setting.emiSettings").toString());
			
			if(!emiSettings.has("bankDisplayName") && !emiSettings.has("emiDisplayText")) return ret;
			
			String bankName = emiSettings.getString("bankDisplayName");
			String displayText = emiSettings.getString("emiDisplayText");
			ret = bankName.concat(" ").concat(displayText);
		} catch (JSONException e) {
			return ret;
		}
		return ret;
	}
	
	public static String getEMIFeeStr(int index) {
		String ret = "EMI Options not configured correctly";
		try {

//			JSONObject emiSettings = (JSONObject) EzetapUIContext.getContext().get("loginresponse.setting.emiSettings");
			if(EzetapUIContext.getContext().get("loginresponse.setting.emiSettings") == null) return ret;
			
			JSONObject emiSettings =  new JSONObject(EzetapUIContext.getContext().get("loginresponse.setting.emiSettings").toString());
			String currCode = (String) EzetapUIContext.getContext().get("loginresponse.currencyCode");
			
			if(!emiSettings.has("bankDisplayName") && !emiSettings.has("emiDisplayText")) return ret;
			
			JSONArray optionCharges = emiSettings.getJSONArray("emiOptionCharges");
			if(optionCharges == null) return ret;
			String emiServiceChargeText = emiSettings.getString("emiServiceChargeDisplayText");
			String charge = optionCharges.getString(index);
			
			ret = emiServiceChargeText.concat(" ").concat(currCode).concat(charge);
			
		} catch (JSONException e) {
			e.printStackTrace();
			return ret;
		}
		
		return ret;
	}
	
	
	/** Basic Arithmetic operators to be called to evaluate values for computed fields 
	 */
	
	public static Double prod(Double d1, Double d2) {
		if(d1 == null || d2 == null) return Double.valueOf("0");
		return d1 * d2;
	}
	
	public static Double prod(Object o1, Object o2) {
		if(o1 == null || o2 == null) return Double.valueOf("0");
		return Double.valueOf(o1.toString()) * Double.valueOf(o2.toString());
	}
	
	public static Double sum(List<Double> data) {
		Double sum = Double.valueOf(0);
		for(int i = 0; i< data.size(); i++) {
			if(data.get(i)!= null) {
				sum = sum + data.get(i);
			}
		}
		return sum;
	}
	
	public static Double quotient(Double d1, Double d2) {
		if(d1 == null) return Double.valueOf(0);
		if(d2 == null) return Double.NaN;
		return d1/d2;
	}
	
	public static Double diff(Double d1, Double d2) {
		if(d1 == null) d1 = Double.valueOf(0);
		if(d2 == null) d2 = Double.valueOf(0);
		return d1 - d2;
	}
	
	public static Double percent(Double d1, Double d2) {
		if(d1 == null || d2 == null) return Double.valueOf(0);
		return d1 * (d2/100);
	}
	
	public static Double sumOf(JSONArray array, String key) {
		Double sum = Double.valueOf(0);
		try {

			for(int i=0; i<array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				sum = sum + o.getDouble(key);
			}
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return sum;
	}
	
	public static String getCountryCode(){
		return "+91";
	}
	
	public static boolean performEquals(Object obj1, Object obj2){
		if(obj1.equals(obj2))
			return true;
		return false;
	}
	
	public static String formatDate(String date){
		String returnDate = date;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", java.util.Locale.ENGLISH);
		SimpleDateFormat expectedFormat = new SimpleDateFormat("EE,  MMM dd hh:mm a", java.util.Locale.ENGLISH);
		
		dateFormat.setLenient(true);
		try {
			returnDate = expectedFormat.format(dateFormat.parse(date));
			
		} catch (ParseException e) {
		}
		if (returnDate == null || returnDate.length() == 0) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", java.util.Locale.ENGLISH);

			dateFormat.setLenient(true);
			try {
				returnDate = expectedFormat.format(dateFormat.parse(date));

			} catch (ParseException e) {
			}
		}
		return returnDate;
	}
	
	public static Double diff(Object o1, Object o2) {
		Double returnVal = Double.valueOf(0);
		if(o1 == null || o2 == null)
			return returnVal;
		Double val1 = Double.valueOf(0);
		Double val2 = Double.valueOf(0);
		if(o1 instanceof Double) {
			val1 = (Double) o1;
		} else {
			val1 = string2double(o1.toString());
		}
		
		if(o2 instanceof Double) {
			val2 = (Double) o2;
		} else {
			val2 = string2double(o2.toString());
		}
		
		returnVal = val1 - val2;
		return returnVal;
	}
	
	public static Double string2double(String str) {
		Double returnVal = Double.valueOf(0);
		try {
			returnVal = Double.valueOf(str);
			java.text.NumberFormat nf = java.text.NumberFormat.getInstance(java.util.Locale.ENGLISH);
			returnVal = Double.valueOf(nf.parse(str).doubleValue());
		} catch (ParseException e) {
		} catch (Exception e) {
		}
		return returnVal;
	}
	
	public static Double sum(Object o1, Object o2) {
		Double returnVal = Double.valueOf(0);
		if(o1 == null || o2 == null)
			return returnVal;
		Double val1 = Double.valueOf(0);
		Double val2 = Double.valueOf(0);
		if(o1 instanceof Double) {
			val1 = (Double) o1;
		} else {
			val1 = string2double(o1.toString());
		}
		
		if(o2 instanceof Double) {
			val2 = (Double) o2;
		} else {
			val2 = string2double(o2.toString());
		}
		
		returnVal = val1 + val2;
		return returnVal;
	}
	
	public static void checkForInitDevice(final Activity activity) {

		boolean cashPaymentEnabled = EzetapUtils.getBooleanValue(EzetapUIContext.getContext().get("loginresponse.setting.cashPaymentEnabled"), true);
		boolean cardPaymentEnabled = EzetapUtils.getBooleanValue(EzetapUIContext.getContext().get("loginresponse.setting.cardPaymentEnabled"), true);
		if(cashPaymentEnabled && !cardPaymentEnabled){
			return;
		}
		boolean usingV1 = EzetapUtils.getBooleanValue(EzetapUIContext.getContext().get("loginresponse.usingV1"), true);
		if(!usingV1) {
			long loginDate = JUtils.getLongSharedPreferrence(
					EzeConstants.KEY_LOGIN_DATE, activity);
			Calendar today = Calendar.getInstance(); // today

			Calendar lastLoginDate = Calendar.getInstance();
			lastLoginDate.setTime(new Date(loginDate)); // last login date

			if (loginDate <= 0 || today.get(Calendar.YEAR) > lastLoginDate.get(Calendar.YEAR)
			  || today.get(Calendar.DAY_OF_YEAR) > lastLoginDate.get(Calendar.DAY_OF_YEAR)) {
				// custom dialog
				final Dialog dialog = new Dialog(activity);
			    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			    dialog.setCancelable(false);
				dialog.setContentView(activity.getResources().getIdentifier("custom_alert_dialog", "layout", activity.getPackageName()));
				// set the title
				TextView title = (TextView) dialog.findViewById(activity.getResources().getIdentifier("dialogTitle", "id", activity.getPackageName()));
				title.setText(UIUtils.getResourceId("str_prepare_device", activity));
				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(activity.getResources().getIdentifier("dialogText", "id", activity.getPackageName()));
				text.setText(UIUtils.getResourceId("str_please_prepare_device",activity));
				// if left button is clicked
				Button leftButton = (Button) dialog.findViewById(activity.getResources().getIdentifier("leftButton", "id", activity.getPackageName()));
				leftButton.setText(UIUtils.getResourceId("btn_later",activity));
				leftButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				// if right button is clicked
				Button rightButton = (Button) dialog.findViewById(activity.getResources().getIdentifier("rightButton", "id", activity.getPackageName()));
				rightButton.setText(UIUtils.getResourceId("btn_now",activity));
				rightButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						new InitializeDongleApihelper().callApi(null, activity, 1234);
						dialog.dismiss();
					}
				});
				JUtils.updateSharedPreferrence(EzeConstants.KEY_LOGIN_DATE, System.currentTimeMillis(), activity);
				dialog.show();
			}
		}
	}
	
	public static String getColorForTxnStatus(String status) {
		if(status.equalsIgnoreCase("AUTHORIZED") || status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("PRE_AUTH")
				|| status.equalsIgnoreCase("CNF_PRE_AUTH"))
			return "#489652";
		else if(status.equalsIgnoreCase("REVERSED") || status.equalsIgnoreCase("VOIDED") || status.equalsIgnoreCase("REFUNDED")
				|| status.equalsIgnoreCase("REL_PRE_AUTH"))
			return "gray";
		else if(status.equalsIgnoreCase("PENDING") || status.equalsIgnoreCase("TC_PENDING") || status.equalsIgnoreCase("REVERSAL_PENDING")
				|| status.equalsIgnoreCase("VOID_PENDING"))
			return "yellow";
		else
			return "RED";
		
	}
	
	public static String getColorForTxnSummaryStatus(String status) {
		if(status.equalsIgnoreCase("AUTHORIZED") || status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("PRE_AUTH")
				|| status.equalsIgnoreCase("CNF_PRE_AUTH"))
			return "#BFBA17";
		else if(status.equalsIgnoreCase("Settled"))
			return "#489652";
		else if(status.equalsIgnoreCase("Settlement Pending"))
			return "#FAA125";
		else 
			return "gray";
	}
	
	public static int getArrayLength(String namespace) {
		try {
			if (namespace != null)
				return ((JSONArray) EzetapUIContext.getContext().get(namespace)).length();
			
		} catch(Exception e) {
		}
		return 0;
	}
	
	public static void setButtonText(Activity activity, int viewId, String text){
		if(activity != null) {
			android.widget.Button view = (android.widget.Button)activity.findViewById(viewId);
			if(view != null && text != null)
				view.setText(text);
		}
	}
	
	public static void setText(Activity activity, int viewId, String text){
		if(activity != null) {
			android.widget.TextView view = (android.widget.TextView)activity.findViewById(viewId);
			if(view != null)
				view.setText(text);
		}
	}
	
	public static boolean isFeedbackOption(String feedbackResponse, String option) {
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			int count = getFeedBackCount();
			
			JSONObject response = (JSONObject) ctx.get(feedbackResponse);
			if(response != null && response.has("feedbacks")) {
				JSONArray feedbacks = response.getJSONArray("feedbacks");
				if(feedbacks != null){
					JSONObject f_back = feedbacks.getJSONObject(count);
					if(f_back != null && f_back.has(option)) {
						return true;
					}
				}
			}
			
		} catch(JSONException e) {}
		return false;
	}

	private static int getFeedBackCount() {
		int count = 0;
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			JSONObject fson = (JSONObject) ctx.get("feedback");
			if(fson == null) {
				setFeedBackCount(0);
				return count;
			}
			if(fson != null && fson.has("count")) {
				Integer count_value = (Integer)fson.get("count");
				if(count_value != null)
					count = count_value.intValue();
			}
		} catch(JSONException e) {}
		return count;
	}
	
	public static void setFeedBackCount(int count) {
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			JSONObject fson = (JSONObject) ctx.get("feedback");
			if(fson == null) {
				fson = new JSONObject();
				fson.put("count", Integer.valueOf(0));
			}
			if(fson != null && fson.has("count")) {
				fson.put("count", count);
			}
			ctx.put("feedback", fson);
		} catch(JSONException e) {}
	}
	
	public static boolean shouldFeedbackSubmit(String feedbackResponse) {
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			int count = getFeedBackCount();
			JSONObject response = (JSONObject) ctx.get(feedbackResponse);
			if(response != null && response.has("feedbacks")) {
				JSONArray feedbacks = response.getJSONArray("feedbacks");
				if(feedbacks != null){
					if(count >= feedbacks.length()) {
						return true;
					}
				}
			}
		} catch(JSONException e) {}
		return false;
	}
	
	public static void setFeedback(String feedback, String feedbackResponse, String question, String option) {
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			JSONObject json = (JSONObject)ctx.get(feedback);
			if(json == null) {
				json = new JSONObject();
				ctx.put(feedback, json);
			}
			int count = getFeedBackCount();
			
			JSONObject response = (JSONObject) ctx.get(feedbackResponse);
			if(response != null && response.has("feedbacks")) {
				JSONArray feedbacks = response.getJSONArray("feedbacks");
				if(feedbacks != null){
					JSONObject f_back = feedbacks.getJSONObject(count);
					if(f_back != null && f_back.has(question) && f_back.has(option)) {
						String key = f_back.getString(question);
						String value = f_back.getString(option);
						json.put(key, value);
						ctx.put(feedback, json);
						setFeedBackCount(count + 1);
					}
				}
			}
		} catch(JSONException e) {}
	}

	public static String getFeedbackValue(String feedbackResponse, String key) {
		String value = new String();
		try {
			EzetapUIContext ctx = EzetapUIContext.getContext();
			int count = getFeedBackCount();
			
			JSONObject response = (JSONObject) ctx.get(feedbackResponse);
			if(response != null && response.has("feedbacks")) {
				JSONArray feedbacks = response.getJSONArray("feedbacks");
				if(feedbacks != null){
					JSONObject f_back = feedbacks.getJSONObject(count);
					if(f_back != null && f_back.has(key)) {
						value = f_back.getString(key);
					}
				}
			}
		} catch(JSONException e) {}
		return value;
	}
	
	
	public static void setJSONValue(String namespace, String value) {
		EzetapUIContext.getContext().put(namespace, value);
	}
	
	public static void setJSONValue(String namespace, int value) {
		EzetapUIContext.getContext().put(namespace, String.valueOf(value));
	}
	
	public static void setJSONValue(String namespace, double value) {
		EzetapUIContext.getContext().put(namespace, String.valueOf(value));
	}
	
	public static boolean shouldSendReceipt() {
		boolean flag = false;
		return flag;
	}
	static Random random = new Random();
	public static String getRandomString(int length) {
		String retVal = "1234567890";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
			sb.append(retVal.charAt(random.nextInt(retVal.length())));
		return sb.toString();
	}
	
	public static boolean shouldSendReceipt(String paymentMode) {
		if((Boolean) EzetapUIContext.getContext().get("loginresponse.setting.emailReceiptEnabled", false)){
			return true;
		}
		if(paymentMode.equals("CARD")) {
			if((Boolean) EzetapUIContext.getContext().get("loginresponse.setting.smsReceiptEnabled", false)) {
				return true;
			}
		} else if(paymentMode.equals("CASH")) {
			if((Boolean) EzetapUIContext.getContext().get("loginresponse.setting.smsReceiptEnabledForCash", false)) {
				return true;
			}
		} else if(paymentMode.equals("CHEQUE")) {
			if((Boolean) EzetapUIContext.getContext().get("loginresponse.setting.smsReceiptEnabledForCheque", false)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean shouldEnableSMSReceipt(String paymentMode) {
		if(paymentMode.equals("CARD")) {
			if((Boolean) EzetapUIContext.getContext().get("loginresponse.setting.smsReceiptEnabled", false)) {
				return true;
			}
		} else if(paymentMode.equals("CASH")) {
			if((Boolean) EzetapUIContext.getContext().get("loginresponse.setting.smsReceiptEnabledForCash", false)) {
				return true;
			}
		} else if(paymentMode.equals("CHEQUE")) {
			if((Boolean) EzetapUIContext.getContext().get("loginresponse.setting.smsReceiptEnabledForCheque", false)) {
				return true;
			}
		}
		return false;

	}
	
	private static int getInteger(Object object) {
		int option = -1;
		if(object != null) {
			if(object instanceof Integer)
				option = ((Integer)object).intValue();
			else if(object instanceof String)
				option = Integer.parseInt((String) object);
			}
		return option;
	}
	 
	public static void startActivity(Activity callingActivity, Class<?> targetActivity) {
		Intent intent = new Intent(callingActivity, targetActivity);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		callingActivity.startActivity(intent);
	}
	
	public static void enableOrHideOption(Activity activity, int viewId, String nameSpace) {
		int option = getInteger(EzetapUIContext.getContext().get(nameSpace, -1));
		switch(option) {
			case -1:
				UIUtils.enableOrHideControl(activity, viewId, false, true);
				break;
			case 0:
				UIUtils.enableOrHideControl(activity, viewId, true, false);
				break;
			case 1:
				UIUtils.enableOrHideControl(activity, viewId, false, false);
				break;
		}
	}
	
	public static void enableOrHideOption(Activity activity, int viewId, String nameSpace1, String nameSpace2) {
		int option1 = getInteger(EzetapUIContext.getContext().get(nameSpace1, -1));
		int option2 = getInteger(EzetapUIContext.getContext().get(nameSpace2, -1));
		if(option1 == 1 && option2 == 1)
			UIUtils.enableOrHideControl(activity, viewId, false, false);
		else if(option1 == -1 || option2 == -1)
			UIUtils.enableOrHideControl(activity, viewId, true, true);
		else if(option1 == 0 || option2 == 0)
			UIUtils.enableOrHideControl(activity, viewId, true, false);	
	}
	
	public static void enableOrDisableOption(Activity activity, int viewId, String nameSpace) {
		int option = getInteger(EzetapUIContext.getContext().get(nameSpace, -1));
		switch(option) {
			case 0:
				UIUtils.enableOrDisableControl(activity, viewId, true);
				break;
			case 1:
				UIUtils.enableOrDisableControl(activity, viewId, false);
				break;
		}
	}
	
	public static boolean showOldOptionsScreen() {
		int preAuthOption = Integer.parseInt((String)EzetapUIContext.getContext().get("loginresponse.setting.preAuthOption", "-1"));
		int cashBackOption = Integer.parseInt((String)EzetapUIContext.getContext().get("loginresponse.setting.cashBackOption", "-1"));
		if(preAuthOption == -1 && cashBackOption == -1)
			return true;
		return false;
	}
	
	public static boolean multipleOptionsEnabled() {
		int count = 0;
		if(isSaleOptionEnabled()) 
			count++;
		if(isPreAuthEnabled())
			count++;
		if(isCashOptionEnabled())
			count++;
		if(count > 1)
			return true;
		return false;
	}
	
	public static boolean bothOptionsEnabled() {
		int preAuthOption = Integer.parseInt((String)EzetapUIContext.getContext().get("loginresponse.setting.preAuthOption", "-1"));
		int cashBackOption = Integer.parseInt((String)EzetapUIContext.getContext().get("loginresponse.setting.cashBackOption", "-1"));
		if(preAuthOption != -1 && cashBackOption != -1)
			return true;
		return false;	
	}
	
	public static int getEnabledOptionsCount() {
		int count = 0;
		if(isSaleOptionEnabled()) 
			count++;
		if(isPreAuthEnabled())
			count++;
		if(isCashOptionEnabled())
			count++;
		return count;
	}
	
	public static boolean noOptionsEnabled() {
		int count = 0;
		if(isSaleOptionEnabled()) 
			count++;
		if(isPreAuthEnabled())
			count++;
		if(isCashOptionEnabled())
			count++;
		if(count < 1)
			return true;
		return false;
	}
	
	public static boolean allOptionsEnabled() {
		if(isPreAuthEnabled() && isCashOptionEnabled() && isSaleOptionEnabled())
			return true;
		return false;
	}

	public static boolean onlyPreAuthEnabled() {
		if(isPreAuthEnabled() && !isSaleOptionEnabled() && !isCashOptionEnabled())
			return true;
		return false;
	}
	
	public static boolean onlySaleEnabled() {
		if(!isPreAuthEnabled() && isSaleOptionEnabled() && !isCashOptionEnabled())
			return true;
		return false;
	}
	
	public static boolean onlyCashEnabled() {
		if(!isPreAuthEnabled() && !isSaleOptionEnabled() && isCashOptionEnabled())
			return true;
		return false;
	}
	
	public static boolean isSalePlusCashEnabled() {
		if(isSaleOptionEnabled() && isCashOptionEnabled())
			return true;
		return false;
	}
	
	public static boolean isPreAuthEnabled() {
		int preAuthOption = getInteger(EzetapUIContext.getContext().get("loginresponse.setting.preAuthOption", -1));
		if(preAuthOption != -1)
			return true;
		return false;
	}
	
	public static boolean isSaleOptionEnabled() {
		int saleOption = getInteger(EzetapUIContext.getContext().get("loginresponse.setting.saleOption", -1));
		if(saleOption != -1)
			return true;
		return false;
	}
	
	public static boolean isCashOptionEnabled() {
		int cashBackOption = getInteger(EzetapUIContext.getContext().get("loginresponse.setting.cashBackOption", -1));
		if(cashBackOption != -1)
			return true;
		return false;
	}
	
	public static boolean checkForOrderList(final Activity activity) {
		String username = EzetapUIContext.getContext().getUserName();
		long loginDate = JUtils.getLongSharedPreferrence(EzeConstants.KEY_LOGIN_DATE, username, activity);
		Calendar today = Calendar.getInstance(); // today
		Calendar lastLoginDate = Calendar.getInstance();
		lastLoginDate.setTime(new Date(loginDate)); // last login date
		if (loginDate <= 0 || today.get(Calendar.YEAR) > lastLoginDate.get(Calendar.YEAR)
		  || today.get(Calendar.DAY_OF_YEAR) > lastLoginDate.get(Calendar.DAY_OF_YEAR)) {
			JUtils.updateSharedPreferrence(EzeConstants.KEY_LOGIN_DATE, System.currentTimeMillis(), username, activity);
			return true;
		}
		return false;
	}
	
	public static void setLastSyncedTime(Activity activity) {
		String username = EzetapUIContext.getContext().getUserName();
		JUtils.updateSharedPreferrence(EzeConstants.KEY_LAST_SYNCED, System.currentTimeMillis(), username, activity);
	}
	
	public static String getLastSyncedTime(Activity activity) {
		String username = EzetapUIContext.getContext().getUserName();
		long loginDate = JUtils.getLongSharedPreferrence(EzeConstants.KEY_LAST_SYNCED, username, activity);
		SimpleDateFormat sdf= new SimpleDateFormat("MMM dd, hh:mm a", java.util.Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(loginDate));
	}
	
	/***
	 * getIMEI : read the IMEI number of device The IMEI (14 decimal digits plus
	 * a check digit) or IMEISV (16 digits) includes information on the origin,
	 * model, and serial number of the device. The structure of the IMEI/SV are
	 * specified in 3GPP TS 23.003
	 * 
	 * @param activity
	 * @return 16 digit IMEI number of device ( padded with zeros)
	 */
	public static String getIMEI(Activity activity) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = telephonyManager.getDeviceId();
			if(imei == null)
				imei = Secure.getString(activity.getContentResolver(), Secure.ANDROID_ID);
			return imei;
		} catch (Exception e) {
		}
		return "EEEE-EEEE-EEEE-E";
	}
	
	/**
	 * Gets the location of ezetap offline data in sdcard
	 * @param a
	 * @return
	 */
	public static File getUserHome() {
		String user = EzetapUIContext.getContext().getUserName();
		File externalFilesDir = new File(Environment.getExternalStorageDirectory().getPath());
		File ezetapHome = new File(externalFilesDir, "ezetap_data");
		ezetapHome.mkdir();
		File userHome = new File(ezetapHome, user);
		userHome.mkdir();
		return userHome;
	}
	
	public static String getUserHomePath() {
		String path = new String();
		File userHome = getUserHome();
		if(userHome != null)
			path = userHome.getAbsolutePath() + "/";
		return path;
	}
	
	public static boolean isIFSCValid(String ifsc) {
		String regex = "[A-Z|a-z]{4}[0][A-Z|a-z|0-9]{6}$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(ifsc).matches();
	}
	
	public static boolean isBankNameValid(String bankName) {
		String regex = "[A-Z|a-z ]*";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(bankName).matches();
	}
	
}
