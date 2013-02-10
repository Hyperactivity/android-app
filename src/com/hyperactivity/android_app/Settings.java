package com.hyperactivity.android_app;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
	public static final String PREF_FILE_NAME = "settingsPrefFile";
	
	private Context context;
	private String id;
	private boolean loaded;
	private ArrayList<Color> colors;
	
	public Settings(Context context) {
		this(context, "");
	}
	
	public Settings(Context context, String id) {
		this.id = id;
		loaded = false;
		colors = new ArrayList<Color>();
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Load settings from android device.
	 * If no settings are found the default settings gets loaded.
	**/
	public void loadLocal() {
		loaded = false; //TODO: how does android handle synchronization? This will do for now.
		
		SharedPreferences prefs = context.getSharedPreferences(Settings.PREF_FILE_NAME, Context.MODE_PRIVATE);
		
		//Read all settings.
		
		
		loaded = true;
	}
	
	/**
	 * Load settings from server.
	 * If no settings are found the default settings gets loaded.
	**/
	public void loadRemote() {
		loaded = false; //TODO: how does android handle synchronization? This will do for now.
		
		if(id.length() > 0) {
			//Load from server
			loaded = true;
		}
		else {
			//Load default
			loadDefault();
		}
	}
	
	/**
	 * Load settings from server.
	 * If no settings are found the default settings gets loaded.
	**/
	public void loadDefault() {
		loaded = false; //TODO: how does android handle synchronization? This will do for now.
		loaded = true;
	}
	
	/**
	 * Save current settings to local android device
	 */
	public void saveLocal() {
		
	}
	
	/**
	 * Save current settings to user on remote server
	 */
	public void saveRemote() {
		
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Color getColor(int index) {
		return colors.get(index);
	}
	
	public boolean addColor(Color color) {
		if(colors.contains(color)) {
			return false;
		}
		
		colors.add(color);
		return true;
	}
	
	public boolean removeColor(Color color) {
		if(!colors.contains(color)) {
			return false;
		}
		
		colors.remove(color);
		return true;
	}
	
	public int numColors() {
		return colors.size();
	}
}
