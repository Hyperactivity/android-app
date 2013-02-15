package com.hyperactivity.android_app.core;

public class RemoteObject {
	private String id; 			//Can be changed to UUID/GUID later depending on how we store this on server.
	private boolean loaded; 	//Tells if information have been loaded from server (only username for now).
	
	public RemoteObject(String id) {
		this.id = id;
		loaded = false;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	protected void load() {
		loaded = true;
	}
		
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	
	public String getId() {
		return id;
	}
}
