package com.hyperactivity.android_app.core;

import java.util.Date;

import android.graphics.Bitmap;

public class Profile {
	private String id;
	private boolean loaded;
	private Bitmap avatar;
	private String description;
	private Date birthdate;
	
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
	
	public Bitmap getAvatar() {
		return avatar;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
}
