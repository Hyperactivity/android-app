package com.hyperactivity.android_app;

public class Account {
	private String id; 			//Can be changed to UUID/GUID later depending on how we store this on server.
	private boolean loaded; 	//Tells if information have been loaded from server (only username for now).
	private String username;
	private Profile profile;
	private Settings settings;
	
	public Account(String id) {
		this.id = id;
		loaded = false;
		profile = new Profile(id);
	}
	
	public String getUsername() {
		return username;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public boolean isLoaded(boolean profileIsLoaded) {
		return loaded && (profile.isLoaded() || (profileIsLoaded ? false : true));
	}
	
	public void load(boolean loadProfile) {
		//load account data from server.
		
		loaded = true;
		
		if(loadProfile) {
			profile.load();
		}
	}
}
