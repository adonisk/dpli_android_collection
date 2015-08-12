
package com.ezetap.android.utils;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.ezetap.android.api.caller.helper.ApiHelperBase;
import com.ezetap.utils.EzetapDownloadActivity;
import com.ezetap.utils.EzetapDownloadUtils;

public class ServiceAppUtils {
	private static final String DEBUG_TAG = "ServiceAppUtils";
	
	public static String findTargetAppPackage(Activity context) {
		Intent intent = new Intent();
		intent.setAction(ApiHelperBase.BASE_PACKAGE + ApiHelperBase.EZETAP_PACKAGE_ACTION);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		
		PackageManager pm = context.getPackageManager();
		
		List<ResolveInfo> availableApps = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		if (availableApps != null) {
			for (ResolveInfo availableApp : availableApps) {
				String packageName = availableApp.activityInfo.packageName;
				if (ApiHelperBase.BASE_PACKAGE.equals(packageName)) {
					return packageName;
				}
			}
		}
		return null;
	}
	
	
	public static AlertDialog showDownloadDialog(final Activity context, int requestcode) {
		Intent intent = new Intent(context, EzetapDownloadActivity.class);
		context.startActivityForResult(intent, requestcode);
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(context);
		downloadDialog.setTitle(UIUtils.getResourceString("str_install_service_app", context));
		downloadDialog.setMessage(UIUtils.getResourceString("msg_install_service_app", context));
		downloadDialog.setPositiveButton(UIUtils.getResourceString("btn_yes", context),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						try {
							EzetapDownloadUtils utils = new EzetapDownloadUtils(ApiHelperBase.APK_URL, context);
							utils.start();
						} catch (ActivityNotFoundException anfe) {
							Log.v(DEBUG_TAG,
									"Could not install Ezetap Service Application");
						}
					}
				});
		
		downloadDialog.setNegativeButton(UIUtils.getResourceString("btn_no", context),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						UIUtils.showToastMessage(UIUtils.getResourceString("msg_service_app_not_installed", context), context);
					}
				});
		return downloadDialog.show();
	}
	
	public static String checkAndDownloadServiceApp(Activity context, int requestcode) {
		String targetAppPackage = findTargetAppPackage(context);
		if (targetAppPackage == null) {
			showDownloadDialog(context, requestcode);
		}
		return targetAppPackage;
	}

}
