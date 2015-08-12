/**
 * 
 */
package com.ezetap.android.context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;

/**
 * @author vivek
 *
 */
public class EzetapUIContext {

	private static EzetapUIContext ctx;
	private static HashMap<String, JSONObject> ctxMap = new HashMap<String, JSONObject>();
	private static String userName = "";
	private static Drawable brandedLogo = null;
	
	private EzetapUIContext(){
		
	}
	
	public static EzetapUIContext getContext(){
		if(ctx==null) ctx = new EzetapUIContext();
		return ctx;
	}
	
	public void forcePut(String namespace, JSONObject o){
			ctxMap.put(namespace, o);
	}
	
	public void put(String key, Object value, int position) {
		if(!key.contains("[")) put(key, value);
		
		String[] keys = key.split("\\.");
		String nameSpace = keys[0];
		if(!ctxMap.containsKey(nameSpace)) {
			ctxMap.put(nameSpace, new JSONObject());
		}
		
		Object o = ctxMap.get(nameSpace);
		
		for(int i=1;i<keys.length;i++ ) {
			if(keys[i].contains("[]")) {
				String arrKey = keys[i].split("\\[")[0];
				if(o instanceof JSONObject) {
					try {
						o = ((JSONObject) o).getJSONArray(arrKey);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else {
				if(o instanceof JSONObject) {
					try {
						o = ((JSONObject) o).get(keys[i]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if(o instanceof JSONArray) {
					try {
						((JSONArray)o).getJSONObject(position).put(keys[i], value);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	public void put(String key, Object value) {
		String[] keys = key.split("\\.");
		String nameSpace = keys[0];
		if(!ctxMap.containsKey(nameSpace)) {
			ctxMap.put(nameSpace, new JSONObject());
		}
		
		Object o = ctxMap.get(nameSpace);
		
		for(int i=1;i<keys.length-1;i++ ) {
			if(keys[i].contains("[]")) {
				String arrKey = keys[i].split("\\[")[0];
				if(o instanceof JSONObject) {
					if(((JSONObject) o).isNull(arrKey)) {
						try {
							((JSONObject) o).put(arrKey, new JSONArray());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					try {
						o = ((JSONObject) o).getJSONArray(arrKey);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else {
				if(o instanceof JSONObject) {
					
					if(((JSONObject) o).isNull(keys[i])) {
						try {
							((JSONObject) o).put(keys[i], new JSONObject());
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
					try {
						o = ((JSONObject) o).get(keys[i]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(o instanceof JSONObject) {
			try {
				((JSONObject)o).put(keys[keys.length-1], value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} 
		if(o instanceof JSONArray) {
			try {
				JSONObject j = new JSONObject();
				j.put(keys[keys.length-1], value);
				((JSONArray)o).put(j);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void put(String key, JSONObject o) {
		String actualKey = getNamespaceName(key);
		
		if(ctxMap.containsKey(actualKey)) {
			JSONObject obj = ctxMap.get(actualKey);
			JSONArray arr = o.names();
			
			for(int i=0; i<o.length(); i++) {
				try {
					obj.put(arr.getString(i), o.get(arr.getString(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			ctxMap.put(actualKey, o);
		}
	}
	
	//Blueprint for a ctx.get
	public Object get(String key) {
		String[] keys = key.split("\\.");
		String nameSpace = keys[0];
		Object o = ctxMap.get(nameSpace);
		for (int i = 1; i < keys.length; i++) {
			if (keys[i].contains("[") && keys[i].contains("]")) {
				String strIndex = keys[i].substring(keys[i].indexOf("[") + 1,
						keys[i].indexOf("]"));
				int index = Integer.parseInt(strIndex);
				String jsonKey = keys[i].substring(0, keys[i].indexOf("["));
				if (o instanceof JSONObject) {
					try {
						if(index < ((JSONArray)((JSONObject) o).get(jsonKey)).length()) {
							o = ((JSONArray)((JSONObject) o).get(jsonKey)).get(index);
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					if(o instanceof JSONObject) {
						if(((JSONObject) o).isNull(keys[i])) o = "";
						else o = ((JSONObject) o).get(keys[i]);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		return o;
	}

	public Object get(String key, Object defaultVal) {
		Object o = get(key);
		if(o == null || o.toString().trim().length()==0) o = defaultVal;
		return o;
	}
	
	
	public JSONObject getJSON(String namespace) {
		if(!ctxMap.containsKey(namespace)) {
			ctxMap.put(namespace, new JSONObject());
		}
		return ctxMap.get(namespace);
	}

	public void removeJSON(String namespace){
		if(ctxMap.containsKey(namespace)) {
			ctxMap.remove(namespace);
		}
		
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		EzetapUIContext.userName = userName;
	}
		
	private String getNamespaceName(String key){
		return splitHelper(key, 0);
	}
	
	private String getActualKey(String key) {
		return splitHelper(key, 1);
	}
	
	private String splitHelper(String s, int indexToReturn) {
		String r = s.split("\\.")[indexToReturn];
		return r.trim();
	}

	public void deserialize(FileInputStream fis) {
		if(ctxMap==null)
			ctxMap = new HashMap<String, JSONObject>();
		// If the context has not been cleared avoid loading from
		// the file
		if(ctxMap.size()>0)
			return;
		try {
        ObjectInputStream ois = new ObjectInputStream(fis);
        userName = ois.readUTF();
        HashMap<String, String> newHasMap  = (HashMap<String, String>) ois.readObject();
        Iterator<String> iterator = newHasMap.keySet().iterator();
        while(iterator.hasNext()){
			String key = iterator.next();
			ctxMap.put(key, new JSONObject(newHasMap.get(key)));
			
		}
        ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void serialize(FileOutputStream fos){
		if(ctxMap!=null && ctxMap.size()>0){
			Iterator<String> iterator = ctxMap.keySet().iterator();
			HashMap<String, String> newHasMap = new HashMap<String, String>();
			while(iterator.hasNext()){
				String key = iterator.next();
				if(ctxMap.get(key)!=null)
				newHasMap.put(key, ctxMap.get(key).toString());
			}
			try {
		        ObjectOutputStream oos = new ObjectOutputStream(fos);
		        oos.writeUTF(userName);
		        oos.writeObject(newHasMap);
		        oos.flush();
		        oos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void clear() {
		if(ctxMap != null)
			ctxMap.clear();
		brandedLogo = null;
	}

	public Drawable getBrandedLogo() {
		return brandedLogo;
	}

	public void setBrandedLogo(Drawable brandedLogo) {
		EzetapUIContext.brandedLogo = brandedLogo;
	}
}
