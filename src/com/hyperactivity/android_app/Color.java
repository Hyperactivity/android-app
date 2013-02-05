package com.hyperactivity.android_app;

public class Color {
	int color;
	String description;
	
	Color() {
		
	}
	
	Color(int color) {
		setColor(color);
	}
	
	Color(String description) {
		setDescription(description);
	}
	
	Color(int color, String description) {
		setColor(color);
		setDescription(description);
	}
	
	Color(String color, String description) {
		setColor(color);
		setDescription(description);
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setColor(String color) {
		this.color = android.graphics.Color.parseColor(color);
	}
}
