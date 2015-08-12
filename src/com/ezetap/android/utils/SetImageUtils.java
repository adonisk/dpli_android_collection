package com.ezetap.android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.ezetap.android.api.caller.helper.ApiHelperBase;
import com.ezetap.android.context.EzetapUIContext;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

public class SetImageUtils {

	public static void setImageForCardType(Activity activity, View v, String cardType) {
		cardType = cardType.toLowerCase();
		if (cardType.contains("visa")) {
			v.setBackgroundResource(activity.getResources().getIdentifier("visa", "drawable", activity.getPackageName()));
		} else if (cardType.contains("master")) {
			v.setBackgroundResource(activity.getResources().getIdentifier("mastercard", "drawable", activity.getPackageName()));
		} else if (cardType.contains("amex")) {
			v.setBackgroundResource(activity.getResources().getIdentifier("amex", "drawable", activity.getPackageName()));
		} else if (cardType.contains("maestro".toLowerCase())) {
			v.setBackgroundResource(activity.getResources().getIdentifier("maestro", "drawable", activity.getPackageName()));
		} else if (cardType.contains("rupay".toLowerCase())) {
			v.setBackgroundResource(activity.getResources().getIdentifier("rupay_logo", "drawable", activity.getPackageName()));
		} else {
			v.setVisibility(View.GONE);
		}
	}

	public static void setImageForPaymentType(Activity activity, View v, String paymentType) {
		if (paymentType.equalsIgnoreCase("CASH")) {
			v.setBackgroundResource(activity.getResources().getIdentifier("cash", "drawable", activity.getPackageName()));
		} else if (paymentType.equalsIgnoreCase("CHEQUE")) {
			v.setBackgroundResource(activity.getResources().getIdentifier("icon_cheque", "drawable", activity.getPackageName()));
		}
	}
	
	public static void setImageForCardType(Activity activity, String id, String cardType) {
		cardType = cardType.toLowerCase();
		if (cardType.contains("visa")) {
			UIUtils.setBackgroundImage(activity, activity.getResources().getIdentifier(id, "id", activity.getPackageName()), activity.getResources().getIdentifier("visa", "drawable", activity.getPackageName()));
		} else if (cardType.contains("master")) {
			UIUtils.setBackgroundImage(activity, activity.getResources().getIdentifier(id, "id", activity.getPackageName()), activity.getResources().getIdentifier("mastercard", "drawable", activity.getPackageName()));
		} else if (cardType.contains("amex")) {
			UIUtils.setBackgroundImage(activity, activity.getResources().getIdentifier(id, "id", activity.getPackageName()), activity.getResources().getIdentifier("amex", "drawable", activity.getPackageName()));
		} else if (cardType.contains("maestro".toLowerCase())) {
			UIUtils.setBackgroundImage(activity, activity.getResources().getIdentifier(id, "id", activity.getPackageName()), activity.getResources().getIdentifier("maestro", "drawable", activity.getPackageName()));
		} else if (cardType.contains("rupay".toLowerCase())) {
			UIUtils.setBackgroundImage(activity, activity.getResources().getIdentifier(id, "id", activity.getPackageName()), activity.getResources().getIdentifier("rupay_logo", "drawable", activity.getPackageName()));
		} else {
			activity.findViewById(activity.getResources().getIdentifier(id, "id", activity.getPackageName())).setVisibility(View.GONE);
		}
	}

	public static void setImageForPaymentType(Activity activity, String id, String paymentType) {
		if (paymentType.equalsIgnoreCase("CASH")) {
			UIUtils.setBackgroundImage(activity, activity.getResources().getIdentifier(id, "id", activity.getPackageName()), activity.getResources().getIdentifier("cash", "drawable", activity.getPackageName()));
		} else if (paymentType.equalsIgnoreCase("CHEQUE")) {
			UIUtils.setBackgroundImage(activity, activity.getResources().getIdentifier(id, "id", activity.getPackageName()), activity.getResources().getIdentifier("icon_cheque", "drawable", activity.getPackageName()));
		}
	}
	
	public static void setImageForOrg(Activity activity, String id, String orgCode) {
		try {
			if(EzetapUIContext.getContext().getBrandedLogo() == null) {
				ImageView v = (ImageView) activity.findViewById(activity.getResources().getIdentifier(id, "id", activity.getPackageName()));
				if(v == null || orgCode == null) return;
				String urlSuffix = "images/logos/";
				String urlString = ApiHelperBase.LOGO_DOWNLOAD_BASE_URL.concat(urlSuffix).concat("ext_merchant_").concat(orgCode.toLowerCase()).concat(".png");
				URL url = new URL(urlString);
				EzetapBrandingLogoFetcher task = new SetImageUtils.EzetapBrandingLogoFetcher(v);
				task.execute(url);
			} else {
				ImageView v = (ImageView) activity.findViewById(activity.getResources().getIdentifier(id, "id", activity.getPackageName()));
				v.setBackgroundDrawable(EzetapUIContext.getContext().getBrandedLogo());
			}
		} catch (MalformedURLException m) {
		} catch (Exception e) {
		}
	}
	
	static class EzetapBrandingLogoFetcher extends AsyncTask<URL, Integer, Drawable>{
		
		private ImageView v;
		
		EzetapBrandingLogoFetcher(ImageView view){
			this.v = view;
		}
		
		@Override
		protected Drawable doInBackground(URL... arg0) {
			Drawable d = null;
			try {
				URL url = arg0[0];
				InputStream content;
				content = (InputStream)url.getContent();
				if(content != null) {
				    d = Drawable.createFromStream(content , "src");
				}
			} catch (IOException e) {
			} 
			return d;
		}
		@Override
		protected void onPostExecute(Drawable logo) {
			if(logo != null) {
				EzetapUIContext.getContext().setBrandedLogo(logo);
				v.setBackgroundDrawable(logo);
			}
	     }
		
	}
}
