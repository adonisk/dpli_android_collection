package com.ezetap.android.api.caller.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ezetap.utils.StringUtils;
import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.android.utils.UIUtils;
import com.ezetap.android.context.EzetapUIContext;
import com.ezetap.android.utils.ServiceAppUtils;
import com.ezetap.utils.EzeConstants;

import android.app.Activity;
import android.content.Intent;

public class CollectionsPayChequeApiHelper extends ApiHelperBase implements ApiHelper {

	EzetapUIContext ctx = EzetapUIContext.getContext();
	
	@Override
	public void preApiCall(JSONObject o, Activity callingActivity) {
	}

	@Override
	public void callApi(JSONObject o, Activity callingActivity, int requestCode) {
		try {
			Intent intent = createIntent();
			
			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
	
			String targetAppPackage = ServiceAppUtils.checkAndDownloadServiceApp(callingActivity, requestCode);
			if (targetAppPackage == null) {
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			intent.putExtra(EzeConstants.KEY_ACTION, EzeConstants.ACTION_PAY_CHEQUE);
			intent.putExtra(EzeConstants.KEY_ENABLE_CUSTOM_LOGIN, false);
			
			intent.putExtra(EzeConstants.KEY_USERNAME, EzetapUIContext.getContext().getUserName());
			
			// Validation for cheque number
			if(StringUtils.hasText(o, EzeConstants.KEY_CHEQUE_NUMBER)) {
				intent.putExtra(EzeConstants.KEY_CHEQUE_NUMBER, o.getString(EzeConstants.KEY_CHEQUE_NUMBER));
			} else {
				UIUtils.showToast("blank_cheque_number", callingActivity);
				return;
			}
			
			// Validation for IFSC Code
			if(StringUtils.hasText(o, EzeConstants.KEY_BANK_CODE)) {
				String bank_code = o.getString(EzeConstants.KEY_BANK_CODE);
				if(EzetapUtils.isIFSCValid(bank_code))
					intent.putExtra(EzeConstants.KEY_BANK_CODE, bank_code);
				else {
					UIUtils.showToast("incorrect_bank_code", callingActivity);
					return;
				}
			}
			
			// Validation for bank name
			if(StringUtils.hasText(o, EzeConstants.KEY_BANK_NAME)) {
				String bank_name = o.getString(EzeConstants.KEY_BANK_NAME).trim();
				if(EzetapUtils.isBankNameValid(bank_name)) {
					intent.putExtra(EzeConstants.KEY_BANK_NAME, bank_name);
					intent.putExtra(EzeConstants.KEY_BANK_NAME_DESC, bank_name); 
				}
				else {
					UIUtils.showToast("incorrect_bank_name", callingActivity);
					return;
				}
			} else {
				UIUtils.showToast("blank_bank_name", callingActivity);
				return;
			}
			
			// Validation for cheque date
			if(StringUtils.hasText(o, EzeConstants.KEY_CHEQUE_DATE)) {
				intent.putExtra(EzeConstants.KEY_CHEQUE_DATE, o.getString(EzeConstants.KEY_CHEQUE_DATE));
			} else {
				com.ezetap.android.utils.UIUtils.showToast("blank_cheque_date", callingActivity);
				return;
			}
			
			double amount = 0;
			JSONObject ad = new JSONObject();
			if(StringUtils.hasText(o, EzeConstants.KEY_AMOUNT)) {
				amount = o.getDouble(EzeConstants.KEY_AMOUNT);
				intent.putExtra(EzeConstants.KEY_AMOUNT, amount);
				ad.put(EzeConstants.KEY_AMOUNT, amount);
			}
			
			if(StringUtils.hasText(o, EzeConstants.KEY_ORDERNO)) {
				ad.put("orderNos", new JSONArray().put(o.getString(EzeConstants.KEY_ORDERNO)));
				intent.putExtra(EzeConstants.KEY_ORDERID, o.getString(EzeConstants.KEY_ORDERNO)); // for DR
			} else {
				String message = "Order Number";
		        if(StringUtils.hasText((String)ctx.get("loginresponse.setting.orderNumberLabel")))
		        	message = (String)ctx.get("loginresponse.setting.orderNumberLabel");
		        UIUtils.showToastMessage(message + " is missing", callingActivity);
				return;
			}
			if(StringUtils.hasText(o, "row_id"))
				intent.putExtra("DBRowID", o.getString("row_id"));
			
			ad.put("_status", "CHEQUE_PAYMENT");
			ad.put("_status_id", "CHEQUE_PAYMENT");
			
			if(StringUtils.hasText(o, EzeConstants.KEY_AMOUNT_COLLECTED)) {
				amount = o.getDouble(EzeConstants.KEY_AMOUNT_COLLECTED);
				intent.putExtra(EzeConstants.KEY_AMOUNT, amount);
				ad.put(EzeConstants.KEY_AMOUNT_COLLECTED, amount);
			} 

			if(amount <= 0) {
				UIUtils.showToastMessage("Invalid amount or amount is missing", callingActivity);
				return;
			}

			if(StringUtils.hasText(o, EzeConstants.KEY_CUSTOMER_MOBILE)) {
				intent.putExtra(EzeConstants.KEY_CUSTOMER_MOBILE, o.getString(EzeConstants.KEY_CUSTOMER_MOBILE));
			}
			if(StringUtils.hasText(o, EzeConstants.KEY_CUSTOMER_NAME)) {
				intent.putExtra(EzeConstants.KEY_CUSTOMER_NAME, o.getString(EzeConstants.KEY_CUSTOMER_NAME));
			}
			
			boolean isDR = false;
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER3)) {
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER3, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER3));
				isDR = o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER3).equalsIgnoreCase("DIGITAL_RECEIPT");
			}
			
			if(isDR) {
				if(!StringUtils.hasText(o, EzeConstants.KEY_CUSTOMER_NAME)) {
					UIUtils.showToastMessage("Customer Name is missing", callingActivity);
					return;
				}
				if(!StringUtils.hasText(o, EzeConstants.KEY_CUSTOMER_MOBILE)) {
					UIUtils.showToastMessage("Customer Mobile Number is missing", callingActivity);
					return;
				}
			}

			String processCode = ctx.get("loginresponse.defaultProcessCode").toString();
			if(StringUtils.hasText(processCode)) {		
				intent.putExtra(EzeConstants.KEY_PROCESS_CODE, processCode);
				if(processCode.equalsIgnoreCase("SALES")) {
					isDR = false;
					if(!StringUtils.hasText(o, "prospectId")) {
						UIUtils.showToastMessage("Prospect Id is missing", callingActivity);
						return;
					}
					if(!StringUtils.hasText(o, EzeConstants.KEY_CUSTOMER_NAME)) {
						UIUtils.showToastMessage("Customer Name is missing", callingActivity);
						return;
					}
					if(!StringUtils.hasText(o, EzeConstants.KEY_CUSTOMER_MOBILE)) {
						UIUtils.showToastMessage("Customer Mobile is missing", callingActivity);
						return;
					}
					if(!StringUtils.hasText(o, "package")) {
						UIUtils.showToastMessage("Package is missing", callingActivity);
						return;
					}
					intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER3, o.getString("prospectId") + "/" + o.getString("package"));
				}
			}			
			
			if(ad != null && !isDR) // Do not send additional data in case of DR
				intent.putExtra(EzeConstants.KEY_ADDITIONAL_DATA, ad.toString());
			
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER2)) {
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER2, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER2));
			}			
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER4)) { 
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER4, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER4));
			}
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER5)) { 
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER5, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER5));
			}			
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER6)) { 
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER6, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER6));
			}	
			if(StringUtils.hasText(o, EzeConstants.KEY_EXTERNAL_REF_NUMBER7)) { 
				intent.putExtra(EzeConstants.KEY_EXTERNAL_REF_NUMBER7, o.getString(EzeConstants.KEY_EXTERNAL_REF_NUMBER7));
			}		
			
			intent.putExtra(EzeConstants.ALLOW_SDK_DEBUGGING, allowSDKDebugging);
			intent.putExtra("isCachingEnabled", isCachingEnabled);
			
			callingActivity.startActivityForResult(intent, requestCode);	

		} catch(JSONException e){
		}
	}

	@Override
	public void postApiCall(JSONObject result, Activity callingActivity) {
		try {
			String processCode = ctx.get("loginresponse.defaultProcessCode").toString();
			if(result != null && result.has("success") && result.getBoolean("success") &&
			   StringUtils.hasText(processCode) && processCode.equalsIgnoreCase("SALES")) {
				JSONObject records = ctx.getJSON("fetchRecordresponse");
				JSONObject payment = ctx.getJSON("paymentdetails");
				if(records != null && payment != null && payment.has("row_id") && records.has("orders")) {
					JSONArray orders = records.getJSONArray("orders");
					JSONArray neworders = new JSONArray();
					if(orders != null && orders.length() > 0) {
						for(int i = 0; i < orders.length(); i++) {
							JSONObject json = orders.getJSONObject(i);
							if(!(json != null && json.has("row_id") 
									&& json.getString("row_id").equalsIgnoreCase(payment.getString("row_id")))) {
								neworders.put(json);
							}
						}
						records.put("orders", neworders);
					}
				}
			}
		} catch (JSONException e) {
		}
	}

	@Override
	public void onApiError(JSONObject result, Activity callingActivity) {
	}

}
