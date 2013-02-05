package com.hyperactivity.android_app;

import java.util.ArrayList;

public class Settings {
	private String id;
	private boolean loaded;
	private ArrayList<Color> colors;
	
	public Settings(String id) {
		this.id = id;
		loaded = false;
		colors = new ArrayList<Color>();
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public void load() {
		//Load data from server
		loaded = true;
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
