/**
 * 
 */
package com.ezetap.utils;

import com.ezetap.utils.EzeConstants;


/**
 * @author JAYESH
 *
 */
public class EzeConstants {

	
	public static final int EZE_RESULT_NO_OPERATION_DEFINED = 4001;
	
	public static final int EZE_RESULT_COULD_PROCESS_TRANSACTION 	= 4002;
	public static final int EZE_RESULT_ACCESS_DENIED			 	= 4003;
	
	public static final String ALLOW_SDK_DEBUGGING = "allowSDKDebugging";
	public static final String IS_CACHING_ENABLED = "isCachingEnabled";
	
	/**
	 * Success but signature is not attached to the transaction.
	 */
	public static final int EZE_RESULT_SUCCESS				 	 	= 4004;
	public static final int EZE_RESULT_SUCCESS_SIGNATURE_ATTACHED	= 4005;
	public static final int EZE_RESULT_LOGOUT						= 4006;

	
	
	public static final int EZE_RESULT_ACCESS_TRANSACTION_DECLINED	= 3001;
	public static final int EZE_RESULT_FAILED						= 3002;
	public static final int EZE_RESULT_CONNECTION_ERROR				= 3003;
	
	
	public static final String KEY_PROXY_IP					= "proxyIP";
	public static final String KEY_PROXY_PORT				= "proxyPort";
	public static final String KEY_PROXY_USER				= "proxyUsername";
	public static final String KEY_PROXY_PASSWORD			= "proxyPassword";
	
	
	public static final String KEY_TIP_ENABLED 				= "tipEnabled";
	public static final String KEY_AUTH_TOKEN 				= "authToken";
	public static final String KEY_AUTH_CODE				= "authCode";
	public static final String KEY_ACTION 					= "action";
	public static final String KEY_USERNAME 				= "username";
	public static final String KEY_ENABLE_CUSTOM_LOGIN		= "enableCustomLogin";
	public static final String KEY_ENABLE_SIGNATURE_CAPTURE	= "captureSignature";
	
	public static final String KEY_APPKEY 					= "appKey";
	public static final String KEY_SERIAL_NUMBER 			= "serialNumber";
	public static final String KEY_SESSION_KEY				= "sessionKey";
	public static final String KEY_MAX_RECORDS				= "maxRecords";
	public static final String KEY_AMOUNT 					= "amount";
	public static final String KEY_AMOUNT_COLLECTED 		= "amountCollected";
	public static final String KEY_TIP_AMOUNT				= "additionalAmount";
	public static final String AMOUNT_CASH_BACK 			= "amountCashBack";
	public static final String KEY_TOTAL_AMOUNT				= "totalAmount";
	public static final String KEY_ORDERID 					= "orderNumber";
	public static final String KEY_ORDERNO 					= "orderNo";
	public static final String KEY_SEARCH_PARAM 			= "searchParam";
	public static final String KEY_CUSTOMER_MOBILE 			= "customerMobileNumber";
	public static final String KEY_CUSTOMER_EMAIL			= "customerEmail";
	public static final String KEY_CUSTOMER_NAME			= "customerName";
	public static final String KEY_CAPTURE_SIGNATURE		= "captureSignature";
	public static final String KEY_TRANSACTION_ID			= "txnId";
	public static final String KEY_TRANSACTION_TYPE			= "txnType";
	public static final String KEY_LOYALTY_TYPE				= "loyaltyType";
	
	public static final String KEY_CHEQUE_NUMBER = "chequeNumber";
	public static final String KEY_BANK_NAME = "bankName";
	public static final String KEY_BANK_CODE = "bankCode";
	public static final String KEY_BANK_NAME_DESC = "bankNameDesc";
	public static final String KEY_BANK_BRANCH = "bankBranch";
	public static final String KEY_CHEQUE_DATE = "chequeDate";
	public static final String KEY_CHEQUE_ISSUED_TO = "chequeIssuedTo";
	
	public static final String KEY_NAME_ON_CARD				= "nameOnCard";
	public static final String KEY_DISPLAY_PAN				= "displayPAN";
	public static final String KEY_USER_AGREEMENT			= "userAgreement";
	public static final String KEY_PAYMENT_MODE 			= "paymentType";
	public static final String KEY_TXN_TIME 				= "transactionTime";
	public static final String KEY_CARD_LAST_4_DIGITS 		= "cardLastFourDigit";

	public static final String KEY_SIGNATURE_ATTACHED 		= "signatureAttached";
	public static final String KEY_IS_VOIDABLE 				= "voidable";

	public static final String KEY_DONE_TASK				= "doneTask";
	
	public static final String KEY_MERCHANT_NAME			= "merchantName";
	
	public static final String KEY_CURRENCY_CODE			= "currencyCode";
	
	public static final String KEY_RESPONSE_DATA 				= "responseData";
	public static final String KEY_REQUEST_DATA 				= "reqData";
	public static final String KEY_SERVER_URL					= "serverUrl";
	public static final String KEY_IS_SUCCESS 					= "isSuccess";
	public static final String KEY_SUCCESS 						= "success";
	
	public static final String KEY_IS_SIGNATURE_ATTACHED 		= "isSignatueAttached";
	public static final String KEY_MESSAGE						= "msg";
	public static final String KEY_ERROR_CODE					= "errorCode";
	public static final String KEY_ERROR_MESSAGE				= "errorMessage";
	public static final String KEY_CARD_TYPE 					= "cardType";
	public static final String KEY_PASSWORD						= "password";
	public static final String KEY_NEW_PASSWORD					= "newPassword";
	public static final String KEY_TRANSACTION_LIST 			= "txns";
	public static final String KEY_JSON_REQ_DATA				= "jsonReqData";
	
	public static final String KEY_PROCESS_DATA_MAP				= "processDataMap";
	public static final String KEY_ADDITIONAL_DATA				= "additionalData";
	public static final String KEY_PROCESS_CODE					= "processCode";
	public static final String KEY_ON_SUCCESS_ACTION			= "onSuccessAction";
	public static final String KEY_ON_SUCCESS_ACTION_JSON_DATA	= "onSuccessActionJsonData";
	public static final String KEY_IS_USER_VALIDATED_BY_MERCHANT			= "isUserValidatedByMerchant";
	public static final String KEY_EXTERNAL_REF_NUMBER 			= "externalRefNumber";
	public static final String KEY_EXTERNAL_REF_NUMBER2 		= "externalRefNumber2";
	public static final String KEY_EXTERNAL_REF_NUMBER3 		= "externalRefNumber3";
	public static final String KEY_EXTERNAL_REF_NUMBER4 		= "externalRefNumber4";
	public static final String KEY_EXTERNAL_REF_NUMBER5 		= "externalRefNumber5";
	public static final String KEY_EXTERNAL_REF_NUMBER6 		= "externalRefNumber6";
	public static final String KEY_EXTERNAL_REF_NUMBER7 		= "externalRefNumber7";
	
	public static final String KEY_USING_CUSTOM_UI 				= "usingCustomUI";
	
	public static final String KEY_NOTIFY_CUSTOMER 				= "notifyCustomer";
	
	public static final String KEY_PRE_SELECTED_TERMINAL_LABEL 	= "preSelectedTerminalLabel";
	
	public static final String KEY_APP = "apps";
	public static final String KEY_APPLICATION_ID = "applicationId";
	public static final String KEY_SERVICE_APP_ID = "ezetap_android_service";
	public static final String KEY_UPDATE_JSON = "updateJSON";
	public static final String KEY_DISPLAY_VERSION = "versionName";
	public static final String KEY_UPGRADE_NOTES = "notes";
	public static final String KEY_UPDATE_URL = "downloadUrl";
	public static final String KEY_UPDATE_SEVERITY = "severity";
	public static final String KEY_HAS_UPGRADE = "upgrade";
	public static final String APPS_DATA = "appData";
	
	public static final String KEY_START_DATE = "startDate";
	public static final String KEY_END_DATE = "endDate";
	
	public static final String KEY_COMPONENT_1 				= "componentOne";
	public static final String KEY_COMPONENT_2 				= "componentTwo";
	public static final String KEY_COMPONENT_3 				= "componentThree";
	
	public static final String KEY_LOGIN_DATE 	= "loginDate";
	public static final String KEY_LAST_SYNCED 	= "lastSyncTime";
	
	public static final String VALUE_SEVERITY_OPTIONAL = "1";
	public static final String VALUE_SEVERITY_MANDATORY = "0";
	
	
	/**
	 * Action code to change user password.
	 */
	public static final String ACTION_CHANGE_PWD			= "change-password";
	
	public static final String KEY_SERIAL_NUM_REQUIRED 		= "serialNumberRequired";
	public static final String ACTION_LOGIN					= "login";
	public static final String ACTION_LOGOUT				= "logout";
	public static final String ACTION_PAYCARD				= "paycard";
	public static final String ACTION_PAY_CASH				= "paycash";
	public static final String ACTION_PAY_CHEQUE			= "paycheque";
	public static final String ACTION_REGISTER_DONGLE		= "registerDongle";
	public static final String ACTION_INITIALIZE_DEVICE		= "initDeviceSession";
	public static final String ACTION_VOID					= "void";
	public static final String ACTION_ATTACH_SIGNATURE		= "attachSignature";
	public static final String ACTION_TXN_HISTORY			= "txnhistory";
	public static final String ACTION_PREAUTH_HISTORY		= "preAuthHistory";
	public static final String ACTION_TODO_LIST				= "todoList";
	public static final String ACTION_FETCH_DELIVERY		= "fetchDelivery";
	public static final String ACTION_TODO_DETAIL			= "todoDetail";
	public static final String ACTION_SAVE_DATA				= "saveData";
	public static final String ACTION_CREATE_SIGNATURE		= "createSignature";
	public static final String ACTION_EZETAB_TAB			= "tabBadgeChange";
	public static final String ACTION_EZETAB_TAB_CHANGE		= "tabChange";
	public static final String ACTION_SYNC_OFFLINE_DATA		= "syncOfflineData";
	public static final String ACTION_UPDATE_TXN			= "updateTransaction";
	public static final String ACTION_AUTHORISE_CARD		= "authorisecard";
	public static final String ACTION_RELEASE_PRE_AUTH		= "releasePreAuth";
	public static final String ACTION_CONFIRM_PRE_AUTH		= "confirmPreAuth";
	public static final String ACTION_CHECK_UPDATES			= "checkUpdates";
	public static final String ACTION_NONCE_CHECK			= "nonceCheck";
	public static final String ACTION_GET_STATUS_CODES		= "getStatusCodes";
	public static final String ACTION_RESET_APPLICATION		= "resetApplication!@#<>?";
	public static final String ACTION_ORDER_LIST 			= "orderList";
	public static final String ACTION_ORDER_UPDATE 			= "orderUpdate";
	
	public static final String ACTION_CHANNEL_SETTING		= "channelChange";
	public static final String ACTION_LOYALTY				= "loyalty";
	public static final String ACTION_LOCALE				= "locale";
	
	public static final String TXN_TYPE_EARN_LOYALTY		= "EARN";
	public static final String TXN_TYPE_BURN_LOYALTY		= "BURN";
	public static final String TXN_TYPE_BALANCE				= "BALANCE";
	
	public static final String TYPE_LOYALTY_CARD			= "SWIPE_CARD";
	public static final String TYPE_LOYALTY_NUMBER			= "NUMBER";
	
	public static final String INTENT_EXTRA_TAB_POSITION	= "TAB_POSITION";
	public static final String INTENT_EXTRA_BADGE_COUNT		= "BADGE_COUNT";
	public static final String INTENT_EXTRA_TAB_ID			= "TAB_ID";
	
	public static final String ERROR_CODE_SESSION_TIMED_OUT = "SESSION_TIMED_OUT";
	public static final String ERROR_CODE_ACCESS_DENIED		= "ACCESS_DENIED";
	
	// result to calling application 
	public static final int RESULT_SUCCESS				= 2001;
	public static final int RESULT_FAILED				= 3001;
	public static final int RESULT_SESSION_TIMEOUT		= 4001;

	
	public static final String OTMK  = "AF05C65F561DF0FC5074710C59F68DF3";
	public static final String OTLEK = "FDAE5D0BAE9DB6BA0423324C200BF702";
	public static final String BANKID =  "EEEE";
	
	public enum Channel {
		AUDIO, USB, USBA, BT, NONE, MOCK;
	}
	
	public enum ChannelSelectionMode {
		AUTO, MANUAL;
	}
	
	public static final String KEY_CHANNEL_MODE = "channelSelectionMode";
	public static final String KEY_CHANNEL = "channelSelection";
	public static final String KEY_LOCALE = "locale";
	
	public static final String KEY_FEEDBACK = "feedback";
	
	public static final String SUPPORT_APK_URL		= "http://d.eze.cc/supportApp";
	
}
