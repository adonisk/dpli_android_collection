package com.ezetap.android.app.properties;

import org.json.JSONException;
import org.json.JSONObject;

public class EzetapProperties {

	public static final String genMode = "DEMO";
	public static final String appId = "dpli_android_collection";
	public static final String appName = "DPLI Collections";
	public static final String appVersion = "11";
	public static final String displayVersion = "1.0";
	public static boolean nonceCheckDone = false;
	
	
	private static JSONObject map = new JSONObject();
	
	@SuppressWarnings("finally")
	public static Object getProperty(String key) {
		Object o = null;
		try {
			if(map.has(key))
				o =map.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return o;
		} finally {
			return o;
		}
	}
	
	public static void putProperty(String key, Object value) {
		try {
			map.put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void clear(){
		map = new JSONObject();
	}
}
