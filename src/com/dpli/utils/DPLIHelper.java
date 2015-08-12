package com.dpli.utils;

import com.ezetap.android.context.EzetapUIContext;
import org.json.JSONObject;
import org.json.JSONException;

public class DPLIHelper 
{

	public static boolean setInstrumentType(String name) 
	{
		JSONObject o = (JSONObject) EzetapUIContext.getContext().get("instrumentDetail");
		if (null == o) o = new JSONObject();
		
		try 
		{
			if ((null != name) && (name == "demandDraft")) 
			{
				o.put("view_title", "DD Details");
				o.put("view_number", "DD Number");
				o.put("view_date", "DD Date");
				o.put("instrumentType", "Demand Draft");
			} 
			else
			{
				o.put("view_title", "Cheque Details");
				o.put("view_number", "Cheque Number");
				o.put("view_date", "Cheque Date");
				o.put("instrumentType", "Cheque");
			}
		} 
		catch (JSONException e) 
		{
		}
		EzetapUIContext.getContext().put("instrumentDetail", o);
		return true;
	}
}
