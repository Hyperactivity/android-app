package com.hyperactivity.android_app;

public class Settings {
	private String id;
	private boolean loaded;
	
	public Settings(String id) {
		this.id = id;
		loaded = false;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public void load() {
		//Load data from server
		loaded = true;
	}
}
