package com.ezetap.android.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import com.ezetap.android.api.caller.helper.ApiHelperBase;
import com.ezetap.android.context.EzetapUIContext;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;

public class SplashImageUtils {

	public static void fetchSplashLogo() {
		try {
			String orgCode = (String)EzetapUIContext.getContext().get("loginresponse.orgCode");
			String urlSuffix = "images/splash/";
			String urlString = ApiHelperBase.LOGO_DOWNLOAD_BASE_URL.concat(urlSuffix).concat(orgCode.toLowerCase(Locale.ENGLISH)).concat(".png");
			URL url = new URL(urlString);
			EzetapSplashImageFetcher task = new SplashImageUtils.EzetapSplashImageFetcher();
			task.execute(url);
			} catch (MalformedURLException m) {
			} catch (Exception e) {
			}
	}
	
	public static Drawable getSplashImage() {
		try {
		   File externalFilesDir = new File(Environment.getExternalStorageDirectory().getPath());
		   File ezetapHome = new File(externalFilesDir, "ezetap_data");
		   ezetapHome.mkdir();
		   File file = new File(ezetapHome.getAbsolutePath(), "img_splash_screen");
		   InputStream is = new FileInputStream(file);
		   return Drawable.createFromStream(is , "src");
		} catch(Exception e) { }	
		return null;
	}
	
	static class EzetapSplashImageFetcher extends AsyncTask<URL, Integer, InputStream>{
		
		EzetapSplashImageFetcher(){
		}
		
		@Override
		protected InputStream doInBackground(URL... arg0) {
			try {
				URL url = arg0[0];
				return (InputStream)url.getContent();
			} catch (IOException e) {
			} 
			return null;
		}
		@Override
		protected void onPostExecute(InputStream content) {
			if(content != null) {
		       try {
		    	   File externalFilesDir = new File(Environment.getExternalStorageDirectory().getPath());
		    	   File ezetapHome = new File(externalFilesDir, "ezetap_data");
		    	   ezetapHome.mkdir();
		           File file = new File(ezetapHome.getAbsolutePath(), "img_splash_screen");
		           OutputStream output = new FileOutputStream(file);
                   final byte[] buffer = new byte[1024];
                   int read;
                   while ((read = content.read(buffer)) != -1)
                       output.write(buffer, 0, read);
                   output.flush();
                   output.close();
	           } catch(Exception e) { }
			} else {
		    	   File externalFilesDir = new File(Environment.getExternalStorageDirectory().getPath());
		    	   File ezetapHome = new File(externalFilesDir, "ezetap_data");
		    	   ezetapHome.mkdir();
		           File file = new File(ezetapHome.getAbsolutePath(), "img_splash_screen");
		           if(file != null && file.exists()) {
		        	   file.delete();
		           }
				// you can delete the image if no logo is found for a new merchant
			}
	     }
		
	}
}