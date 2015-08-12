package com.dpli.api.caller.helper;

import com.ezetap.android.api.caller.helper.PayChequeApiHelper;

import org.json.JSONException;
import org.json.JSONObject;
import com.ezetap.android.context.EzetapUIContext;
import android.app.Activity;

public class DPLIPayChequeHelper extends PayChequeApiHelper 
{
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) 
	{
		JSONObject instrument = (JSONObject)EzetapUIContext.getContext().get("instrumentDetail");
		
		if ((null!= o) && (null != instrument))
		{
			try
			{
				o.put("chequeNumber", instrument.getString("chequeNumber"));
				o.put("chequeDate", instrument.getString("chequeDate"));
				o.put("bankName", instrument.getString("bankName"));
				o.put("bankCode", instrument.getString("bankCode"));
				o.put("externalRefNumber2", instrument.getString("instrumentType"));
				o.put("externalRefNumber3", instrument.getString("clearingType"));
			}
			catch (JSONException e) 
			{
			}
		}
	}	

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) 
	{
		EzetapUIContext context = EzetapUIContext.getContext();
		context.put("paymentResult", result);
		context.removeJSON("paymentDetail");
		context.removeJSON("instrumentDetail");
	}

}
