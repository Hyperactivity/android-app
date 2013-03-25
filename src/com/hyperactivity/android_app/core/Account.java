package com.hyperactivity.android_app.core;

public class Account extends RemoteObject{
	private String username;
	private Profile profile;
	private Settings settings;
	
	public Account(String id) {
		this(id, "");
	}
	
	public Account(String id, String username) {
		super(id);
		this.username = username;
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
	
	public boolean isLoaded(boolean profileIsLoaded) {
		return isLoaded() && (profile.isLoaded() || (profileIsLoaded ? false : true));
	}
	
	public void load(boolean loadProfile) {
		//load account data from server.
		
		if(loadProfile) {
			profile.load();
		}
		
		super.load();
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
