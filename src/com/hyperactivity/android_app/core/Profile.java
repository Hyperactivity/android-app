package com.hyperactivity.android_app.core;

import java.util.Date;

import android.graphics.Bitmap;

public class Profile extends RemoteObject{
	private Bitmap avatar;
	private String description;
	private Date birthdate;
	
	public Profile(String id) {
		super(id);
	}

	public void load() {
		//Load data from server
		
		super.load();
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
	
	public void setAvatar(Bitmap avatar) {
		this.avatar = avatar;
	}
	
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
