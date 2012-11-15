package razzleDazzlers.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import razzleDazzlers.mycafemac.MenuActivity;

public class Cache {
	
	public Cache(){}
	
	public void setStringArrayPref(Context context, String key, ArrayList<String> values) {
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    JSONArray a = new JSONArray();
	    for (int i = 0; i < values.size(); i++) {
	        a.put(values.get(i));
	    }
	    if (!values.isEmpty()) {
	        editor.putString(key, a.toString());
	    } else {
	        editor.putString(key, null);
	    }
	    editor.commit();
	}
	
	public static ArrayList<String> getStringArrayPref(Context context, String key) {
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    String json = prefs.getString(key, null);
	    ArrayList<String> lst = new ArrayList<String>();
	    if (json != null) {
	        try {
	            JSONArray a = new JSONArray(json);
	            for (int i = 0; i < a.length(); i++) {
	                String entry = a.optString(i);
	                lst.add(entry);
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    }
	    return lst;
	}
	
	public void setDatePref(Context context, String key, String value) {
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	
	public String getDatePref(Context context, String key) {
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    String value = prefs.getString(key, null);
	    return value;
	}

}
