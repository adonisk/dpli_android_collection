package com.ezetap.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;

import com.ezetap.android.api.caller.helper.ApiHelperBase;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.JUtils;
import com.ezetap.utils.StringUtils;
import com.ezetap.utils.EzeConstants.Channel;
import com.ezetap.utils.EzeConstants.ChannelSelectionMode;

public class JUtils {
	
	public static void removeSharedPreferrence(String aKey, Activity activity) {
		if (activity == null || aKey == null)
			return;

		SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.remove(aKey);

		editor.commit();
	}

	public static void updateSharedPreferrence(String aKey, String aVal, Activity activity) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(aKey, aVal);
		editor.commit();;
	}

	public static void updateSharedPreferrence(String aKey, boolean aVal, Activity activity) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(aKey, aVal);
		editor.commit();
	}

	static final String FILE_NAME = "app.db";

	public static void updateSharedPreferrence(Hashtable<String, String> htMap, Activity activity) {

		if (activity == null)
			return;

		SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = sharedPreferences.edit();

		Enumeration<String> enums = htMap.keys();
		while (enums.hasMoreElements()) {
			String aKey = (String) enums.nextElement();
			String aVal = htMap.get(aKey);
			editor.putString(aKey, aVal);
		}

		editor.commit();
	}

	public static void updateBooleanSharedPreferrence(Hashtable<String, Boolean> htMap, Activity activity) {

		if (activity == null)
			return;

		SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = sharedPreferences.edit();

		Enumeration<String> enums = htMap.keys();
		while (enums.hasMoreElements()) {
			String aKey = (String) enums.nextElement();
			Boolean aVal = htMap.get(aKey);
			editor.putBoolean(aKey, aVal);
		}

		editor.commit();
	}

	public static void updateSharedPreferrence(String aKey, long aVal, Activity activity) {

		if (activity == null)
			return;
		SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(aKey, aVal);
		editor.commit();
	}

	public static void updateSharedPreferrence(String aKey, int aVal, Activity activity) {

		if (activity == null)
			return;
		SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(aKey, aVal);
		editor.commit();
	}

	public static String getSharedPreferrence(String inKey, Activity anActivity) {
		if (anActivity != null) {
			SharedPreferences sharedPreferences = anActivity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			Map<String, ?> map = (Map<String, ?>) sharedPreferences.getAll();

			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String aKey = (String) iterator.next();
				Object obj = map.get(aKey);

				if (obj != null && obj instanceof String && aKey.equals(inKey)) {
					return obj.toString();
				}
			}
		}
		return null;

	}

	public static long getLongSharedPreferrence(String inKey, String userName, Activity anActivity) {
		if (anActivity != null) {
			SharedPreferences sharedPreferences = anActivity.getSharedPreferences(userName + FILE_NAME, Context.MODE_PRIVATE);
			return sharedPreferences.getLong(inKey, 0);
		}
		return 0;
	}
	
	public static void updateSharedPreferrence(String aKey, long aVal, String userName, Activity activity) {
		if (activity == null)
			return;
		SharedPreferences sharedPreferences = activity.getSharedPreferences(userName + FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(aKey, aVal);
		editor.commit();
	}
	
	public static long getLongSharedPreferrence(String inKey, Activity anActivity) {
		if (anActivity != null) {
			SharedPreferences sharedPreferences = anActivity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			return sharedPreferences.getLong(inKey, 0);
		}
		return 0;
	}

	public static Integer getIntegerSharedPreferrence(String inKey, Activity anActivity) {
		if (anActivity != null) {
			SharedPreferences sharedPreferences = anActivity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			return sharedPreferences.getInt(inKey, 0);
		}
		return 0;
	}

	public static boolean getBooleanSharedPreferrence(String inKey, Activity anActivity) {
		if (anActivity != null) {
			SharedPreferences sharedPreferences = anActivity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			boolean defVal = false;
			return sharedPreferences.getBoolean(inKey, defVal);
		}
		return false;
	}
	
	public static void startLauncherActivity(Activity activity) {
		PackageManager pm = activity.getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(activity.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("activity_restart", true);
		activity.startActivity(intent);
	}
	
	public static void setChannel(Channel channel, Activity activity){
		ChannelSelectionMode mode = ChannelSelectionMode.MANUAL;
		if(channel == Channel.NONE)
			mode = ChannelSelectionMode.AUTO;
		if(channel == null || mode == null) return;
		updateSharedPreferrence(EzeConstants.KEY_CHANNEL_MODE, String.valueOf(mode), activity);
		updateSharedPreferrence(EzeConstants.KEY_CHANNEL, String.valueOf(channel), activity);
		sendChannelToServiceApp(activity);
	}
	
	public static Locale getLocale(Activity activity) {
		String language = getLanguage(activity);
		return new java.util.Locale(language);
	}
	
	public static String getLanguage(Activity activity) {
		String language = new String("en");
		EzetapUIContext ctx = EzetapUIContext.getContext();
		if(EzetapUtils.getBooleanValue(ctx.get("loginresponse.setting.localizationEnabled"), false)) {
			if(StringUtils.hasText((String)ctx.get("loginresponse.locale"))) {
				language = (String)ctx.get("loginresponse.locale");
				if(language == null)
					language = new String("en");
				else if(language.length() != 2)
					language = new String("en");
			}
		}
		return language;
	}
	
	public static void resetLanguageIcon(Activity activity, int viewID, String lang, String icon) {
		String language = getLanguage(activity);
		boolean flag = (language.equalsIgnoreCase(lang));
		if(flag) {
			Button view = (Button) activity.findViewById(viewID);
			if(view != null) {
				int image = activity.getResources().getIdentifier(icon, "drawable", activity.getPackageName());
				view.setCompoundDrawablesWithIntrinsicBounds(image, 0, 0, 0);
//				view.setEnabled(!flag);
			}
		}
	}
	
	public static void setJSON(String value, String namespace) {
		EzetapUIContext ctx = EzetapUIContext.getContext();
		ctx.put(value, namespace);
	}
	
	public static String getChannel(Activity activity) {
		return getSharedPreferrence(EzeConstants.KEY_CHANNEL, activity);
	}
	
	public static String getChannelMode(Activity activity) {
		return getSharedPreferrence(EzeConstants.KEY_CHANNEL_MODE, activity);
	}
	
	public static void resetChannelButton(Activity activity, Channel channel, int viewID) {
		String savedChannel = getChannel(activity);
		boolean flag = false;
		if(savedChannel == null) {
			updateSharedPreferrence(EzeConstants.KEY_CHANNEL, String.valueOf(Channel.NONE), activity);
			updateSharedPreferrence(EzeConstants.KEY_CHANNEL_MODE, String.valueOf(ChannelSelectionMode.AUTO), activity);
			savedChannel = String.valueOf(Channel.NONE);
		}
		flag = (Channel.valueOf(savedChannel) == channel);
		View view = activity.findViewById(viewID);
		view.setEnabled(!flag);
	}
	
	public static int getFeedBackCount(Activity activity, String namespace) {
		String feedback = getSharedPreferrence(EzeConstants.KEY_FEEDBACK, activity);
		int count = 0;
		try {
			if(feedback != null) {
				JSONObject savedFB = new JSONObject(feedback);
				if(savedFB.has(namespace)) {
					String value = savedFB.getString(namespace);
					count = Integer.parseInt(value);
				}
			}
		} catch(JSONException e) {
		}
		catch(Exception e){
		}
		return count;
	}
	
	public static void saveFeedBack(Activity activity, String namespace) {
		String feedback = getSharedPreferrence(EzeConstants.KEY_FEEDBACK, activity);
		try {
			JSONObject json =  (JSONObject)EzetapUIContext.getContext().get(namespace);
			JSONObject savedFB = new JSONObject();
			if(feedback != null)
				savedFB = new JSONObject(feedback);
			if(savedFB != null && json != null) {
				Iterator<String> keys = json.keys();
				while(keys.hasNext()) {
					String key = keys.next();
					String value = json.getString(key);
					savedFB.put(key, value);
				}
				updateSharedPreferrence(EzeConstants.KEY_FEEDBACK, savedFB.toString(), activity);
			}
		} catch(JSONException e) {
		}
	} 
	
	private static void sendChannelToServiceApp(Activity callingActivity) {
		if(!EzetapUtils.checkConnectivity(callingActivity)) return;
		try {
		Intent intent = new Intent();
		
		intent.setAction(ApiHelperBase.BASE_PACKAGE + ApiHelperBase.EZETAP_PACKAGE_ACTION);
		intent.addCategory(Intent.CATEGORY_DEFAULT);

		String targetAppPackage = ServiceAppUtils.checkAndDownloadServiceApp(callingActivity, 500);
		if (targetAppPackage == null) {
			return;
		}
		intent.setPackage(targetAppPackage);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_CHANNEL_SETTING);
		intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);

		String mode = JUtils.getChannelMode(callingActivity);
		String channel = JUtils.getChannel(callingActivity);
		intent.putExtra(EzeConstants.KEY_CHANNEL_MODE, mode);
		intent.putExtra(EzeConstants.KEY_CHANNEL, channel);
		intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
		callingActivity.startActivity(intent);		

		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static boolean isPureAscii(String v) {
		//Removing the non-ascii characters from the string. This is for localization
		v = v.replaceAll("[^\\p{ASCII}]", "");
		byte bytearray []  = v.getBytes();
		java.nio.charset.CharsetDecoder d = java.nio.charset.Charset.forName("US-ASCII").newDecoder();
	    try {
	    	java.nio.CharBuffer r = d.decode(java.nio.ByteBuffer.wrap(bytearray));
	    	r.toString();
	    }
	    catch(java.nio.charset.CharacterCodingException e) {
	    	return false;
	    }
	    return true;
	}
	
	public static String isJSONValid(String jsonString) {
		try {
			JSONObject json = new JSONObject(jsonString);
			Iterator<?> keys = json.keys();
			while(keys.hasNext()) {
				String key = (String)keys.next();
				String value = json.getString(key);
				if(!isPureAscii(value)) {
					return value;
	            }
			}
		} catch (JSONException e) {
			return "exception";
		}
		return "success";
	}
	
	public static boolean isEmailValid(String emailID) {
		if(emailID == null)
			return false;
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return emailID.matches(EMAIL_REGEX);
	}
}