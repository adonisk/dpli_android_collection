package com.ezetap.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ezetap.android.utils.EzetapUtils;
import com.ezetap.utils.EzeConstants;
import com.ezetap.utils.RSAUtil;

public class OfflineContentManager {

	public static double getPendingRequestAmount() {
		List<RequestBean> requests = getPendingRequests();
		double amount = 0;
		if (requests != null) {
			for (RequestBean requestBean : requests) {
				try {
					String apiName = requestBean.getApiName();
					if(apiName.equalsIgnoreCase(EzeConstants.ACTION_PAY_CASH) || 
							apiName.equalsIgnoreCase(EzeConstants.ACTION_PAY_CHEQUE)) {
						byte[] data = RSAUtil.decrypt(requestBean.getData());
						JSONObject offlineRequest = new JSONObject(new String(data));
						if(offlineRequest != null && offlineRequest.has("amount")) {
							amount = amount + offlineRequest.getDouble("amount");
						}
					}
				} catch (JSONException e) {
				} catch (Exception e) {
				}
			}
		}
		return amount;
	}
	
	public static int getPendingRequestCount() {
		List<RequestBean> requests = getPendingRequests();
		int count = 0;
		if (requests != null) {
			for (RequestBean requestBean : requests) {
				try {
					String apiName = requestBean.getApiName();
					if(apiName.equalsIgnoreCase(EzeConstants.ACTION_PAY_CASH) || 
							apiName.equalsIgnoreCase(EzeConstants.ACTION_PAY_CHEQUE)) {
						count = count + 1;
					}
				} catch (Exception e) {
				}
			}
		}
		return count;
	}
	
	@SuppressWarnings("finally")
	private static List<RequestBean> getPendingRequests() {
		DataInputStream dis = null;
		List<RequestBean> apiList = new ArrayList<RequestBean>();
		try {
			File userHome = EzetapUtils.getUserHome();
			if (userHome == null)
				return apiList; // this means userhome could not be determined.
								// either there is no SD card or there's
								// insufficient space. caching should have been
								// turned off
			File apiHome = new File(userHome, "api");
			if (!apiHome.exists())
				apiHome.mkdirs();
			File requestDir = new File(apiHome, "request");
			if (!requestDir.exists())
				requestDir.mkdir();
			File[] apiDirs = requestDir.listFiles();

			for (int i = 0; i < apiDirs.length; i++) {
				File[] pendingFiles = apiDirs[i].listFiles();
				for (int j = 0; j < pendingFiles.length; j++) {
					String[] parts = pendingFiles[j].getParent().split("/");
					String api = parts[parts.length - 1];
					dis = new DataInputStream(new FileInputStream(pendingFiles[j]));
					byte[] data = new byte[dis.available()];
					dis.read(data);
					String fileId = pendingFiles[j].getName();
					apiList.add(new RequestBean(api, fileId, data));
				}
			}

		} catch (IOException e) {
		} finally {
			try {
				if (dis != null)
					dis.close();
			} catch (IOException e) {
			}
			return apiList;
		}
	}
	
	static class RequestBean {
		private String apiName;
		private String fileIdentifier;
		private byte[] data;

		public RequestBean(String apiName, String fileId, byte[] data) {
			this.apiName = apiName;
			this.fileIdentifier = fileId;
			this.data = data;
		}

		public String getApiName() {
			return apiName;
		}

		public void setApiName(String apiName) {
			this.apiName = apiName;
		}

		public String getFileIdentifier() {
			return fileIdentifier;
		}

		public void setFileIdentifier(String fileIdentifier) {
			this.fileIdentifier = fileIdentifier;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

	}
}
