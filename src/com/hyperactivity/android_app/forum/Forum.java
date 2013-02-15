package com.hyperactivity.android_app.forum;

import java.util.LinkedList;
import java.util.List;

import com.hyperactivity.android_app.core.Color;

public class Forum {
	private List<Category> categories;
	
	public Forum() {
		this.categories = new LinkedList<Category>();
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
	public void addCategory(Category category) {
		if(!categories.contains(category)) {
			categories.add(category);
		}
	}
	
	public void createCategory(String subject, Color color) {
		addCategory(new Category(subject, color));
	}
}
