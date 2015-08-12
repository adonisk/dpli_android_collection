package com.ezetap.android.api.caller.helper;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.android.utils.EzetapConstDef;
import com.ezetap.android.app.properties.EzetapProperties;
import com.ezetap.utils.EzeConstants;

public abstract class ApiHelperBase implements ApiHelper {
	
	public static String DBG_HOST_IP = "d.eze.cc";
	
	public static String MODE = "demo";
	
	public static String LOGO_DOWNLOAD_BASE_URL				= "http://d.eze.cc/";
	public static String BASE_PACKAGE						= "com.ezetap.service.demo";
	public static String APK_URL							= "http://d.eze.cc/demo/app/android/serviceApp";
	public static String SUPPORT_APK_URL					= "http://d.eze.cc/demo/app/android/ezetap_android_support";
	
	
	public static final String PROD_LOGO_DOWNLOAD_BASE_URL	= "http://d.eze.cc/";
	public static final String PROD_BASE_PACKAGE			= "com.ezetap.service.prod";
	public static final String PROD_APK_URL					= "https://www.ezetap.com/portal/app/android/serviceApp";
	public static final String PROD_SUPPORT_APK_URL			= "https://www.ezetap.com/portal/app/android/ezetap_android_support";
	
	
	public static final String PRE_PROD_LOGO_DOWNLOAD_BASE_URL	= "http://pp.eze.cc/";
	public static final String PRE_PROD_BASE_PACKAGE			= "com.ezetap.service.preprod";
	public static final String PRE_PROD_APK_URL					= "http://pp.eze.cc/portal/app/android/serviceApp";
	public static final String PRE_PROD_SUPPORT_APK_URL			= "http://pp.eze.cc/portal/app/android/ezetap_android_support";
	
	
	public static final String DEMO_LOGO_DOWNLOAD_BASE_URL	= "http://d.eze.cc/";
	public static final String DEMO_BASE_PACKAGE			= "com.ezetap.service.demo";
	public static final String DEMO_APK_URL					= "http://d.eze.cc/demo/app/android/serviceApp";
	public static final String DEMO_SUPPORT_APK_URL			= "http://d.eze.cc/demo/app/android/ezetap_android_support";
	
	
	public static final String OTHER_LOGO_DOWNLOAD_BASE_URL = "http://d.eze.cc/";
	public static final String OTHER_BASE_PACKAGE			= "com.ezetap.service";
	public static final String OTHER_APK_URL				= "https://www.ezetap.com/portal/app/android/serviceApp";
	public static final String OTHER_SUPPORT_APK_URL		= "http://d.eze.cc/demo/app/android/ezetap_android_support";
	
	
	public static final String MOCK_LOGO_DOWNLOAD_BASE_URL = "http://d.eze.cc/";
	public static final String MOCK_BASE_PACKAGE			= "com.ezetap.service.mock";
	public static final String MOCK_APK_URL				= "http://d.eze.cc/mock/app/android/serviceApp";
	public static final String MOCK_SUPPORT_APK_URL		= "http://d.eze.cc/demo/app/android/ezetap_android_support";
	
	public static final String MODE_DEMO					= "demo";
	public static final String MODE_PRE_PROD				= "preprod";
	public static final String MODE_PROD					= "prod";
	public static final String MODE_OTHER					= "other";
	public static final String MODE_MOCK					= "mock";
	
	public static final String EZETAP_PACKAGE_ACTION = ".EZESERV";
	private static final String DEBUG_TAG = "EzeUtils";
	
	protected static final String APP_ID = "appId";
	protected static final String APP_NAME = "appName";
	protected static final String APP_VERSION = "appVersion";
	protected static final String DISPLAY_VERSION = "displayVersion";
	protected static final String HAS_VERSION_INFO = "hasVersionInfo";
	
	protected static final boolean allowSDKDebugging = false;
	protected static final boolean isCachingEnabled = true;
	public static boolean isFullRefresh = true;

	/* (non-Javadoc)
	 * @see com.ezetap.android.api.caller.helper.ApiHelper#callApi(org.json.JSONObject, android.app.Activity)
	 */
	public abstract void preApiCall(JSONObject o, Activity callingActivity);
	public abstract void callApi(JSONObject o, Activity callingActivity, int requestCode);
	public abstract void postApiCall(JSONObject result, Activity callingActivity);
	public abstract void onApiError(JSONObject result, Activity callingActivity);
	
	protected  String findTargetAppPackage(Intent intent, Activity context) {
		return ServiceAppUtils.findTargetAppPackage(context);
	}
	
	protected AlertDialog showDownloadDialog(final Activity context, int requestcode) {
		return ServiceAppUtils.showDownloadDialog(context, requestcode);
	}
	
	public static void changeApplicationMode(String mode){
		
		if(mode.equalsIgnoreCase(MODE_DEMO)){
			LOGO_DOWNLOAD_BASE_URL = DEMO_LOGO_DOWNLOAD_BASE_URL;
			BASE_PACKAGE = DEMO_BASE_PACKAGE;
			APK_URL = DEMO_APK_URL;
			SUPPORT_APK_URL = DEMO_SUPPORT_APK_URL;
		}else if(mode.equalsIgnoreCase(MODE_PRE_PROD)){
			LOGO_DOWNLOAD_BASE_URL = PRE_PROD_LOGO_DOWNLOAD_BASE_URL;
			BASE_PACKAGE = PRE_PROD_BASE_PACKAGE;
			APK_URL = PRE_PROD_APK_URL;
			SUPPORT_APK_URL = PRE_PROD_SUPPORT_APK_URL;
		}else if(mode.equalsIgnoreCase(MODE_PROD)){
			LOGO_DOWNLOAD_BASE_URL = PROD_LOGO_DOWNLOAD_BASE_URL;
			BASE_PACKAGE = PROD_BASE_PACKAGE;
			APK_URL = PROD_APK_URL;
			SUPPORT_APK_URL = PROD_SUPPORT_APK_URL;
		}else if(mode.equalsIgnoreCase(MODE_MOCK)){
			LOGO_DOWNLOAD_BASE_URL = MOCK_LOGO_DOWNLOAD_BASE_URL;
			BASE_PACKAGE = MOCK_BASE_PACKAGE;
			APK_URL = MOCK_APK_URL;
			SUPPORT_APK_URL = DEMO_SUPPORT_APK_URL;
		}else{
			LOGO_DOWNLOAD_BASE_URL = OTHER_LOGO_DOWNLOAD_BASE_URL;
			BASE_PACKAGE = OTHER_BASE_PACKAGE;
			APK_URL = OTHER_APK_URL;
			SUPPORT_APK_URL = OTHER_SUPPORT_APK_URL;
		}
		MODE = mode;
	}
	
	public static void setHostIPAddress(String ipAddress){
		DBG_HOST_IP = ipAddress;
	}
	protected final Intent createIntent() {
		Intent intent = new Intent();
		intent.putExtra(APP_ID, EzetapProperties.appId);
		intent.putExtra(APP_NAME, EzetapProperties.appName);
		intent.putExtra(APP_VERSION, EzetapProperties.appVersion);
		intent.putExtra(DISPLAY_VERSION, EzetapProperties.displayVersion);
		intent.putExtra(HAS_VERSION_INFO, true);
		intent.putExtra(EzetapConstDef.DBG_HOST, DBG_HOST_IP);
		intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
		intent.putExtra(EzeConstants.IS_CACHING_ENABLED, isCachingEnabled);
		return intent;
	}	
	
}
