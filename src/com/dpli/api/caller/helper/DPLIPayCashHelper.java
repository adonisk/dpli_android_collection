package com.dpli.api.caller.helper;

import com.ezetap.android.api.caller.helper.PayCashApiHelper;
import com.ezetap.android.context.EzetapUIContext;
import org.json.JSONObject;
import android.app.Activity;

public class DPLIPayCashHelper extends PayCashApiHelper 
{
	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) 
	{
		EzetapUIContext context = EzetapUIContext.getContext();
		context.put("paymentResult", result);
		context.removeJSON("paymentDetail");
		context.removeJSON("instrumentDetail");
	}

}
