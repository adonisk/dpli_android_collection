package com.ezetap.android.api.caller;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;

import com.ezetap.android.api.caller.helper.ApiHelper;

public class ApiCaller {

	private static HashMap<String, ApiHelper> apiMap = new HashMap<String, ApiHelper>();
	
	static{
		try {
						apiMap.put("payCash", com.dpli.api.caller.helper.DPLIPayCashHelper.class.newInstance());
			apiMap.put("txnDetail", com.ezetap.android.api.caller.helper.TransactionDetailApiHelper.class.newInstance());
			apiMap.put("applyUpdates", com.ezetap.android.api.caller.helper.EzetapUpdateApiHelper.class.newInstance());
			apiMap.put("changePassword", com.ezetap.android.api.caller.helper.ChangePasswordApiHelper.class.newInstance());
			apiMap.put("payCheque", com.dpli.api.caller.helper.DPLIPayChequeHelper.class.newInstance());
			apiMap.put("fetchPolicy", com.dpli.api.caller.helper.FetchPolicyApiHelper.class.newInstance());
			apiMap.put("payCard", com.dpli.api.caller.helper.DPLIPayCardHelper.class.newInstance());
			apiMap.put("login", com.ezetap.android.api.caller.helper.LoginApiHelper.class.newInstance());
			apiMap.put("updateTxn", com.ezetap.android.api.caller.helper.UpdateTransactionApiHelper.class.newInstance());
			apiMap.put("txnHistory", com.ezetap.android.api.caller.helper.TransactionHistoryApiHelper.class.newInstance());
			apiMap.put("initDevice", com.ezetap.android.api.caller.helper.InitializeDongleApihelper.class.newInstance());
			apiMap.put("logout", com.ezetap.android.api.caller.helper.LogoutApiHelper.class.newInstance());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	public static void callApi(String name, JSONObject o, Activity callingActivity, int requestCode) {
		ApiHelper helper = apiMap.get(name);
		helper.callApi(o, callingActivity, requestCode);
	}
	
	public static void preApiCall(String name, JSONObject o, Activity callingActivity) {
		ApiHelper helper = apiMap.get(name);
		helper.preApiCall(o, callingActivity);
	}
	
	public static void postApiCall(String name, JSONObject o, Activity callingActivity) {
		ApiHelper helper = apiMap.get(name);
		helper.postApiCall(o, callingActivity);
	}
	
	public static void onApiError(String name, JSONObject o, Activity callingActivity) {
		ApiHelper helper = apiMap.get(name);
		helper.onApiError(o, callingActivity);
	}
}
