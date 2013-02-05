package com.hyperactivity.android_app;

public class Profile {
	private String id;
	private boolean loaded;
	
	public Profile(String id) {
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
