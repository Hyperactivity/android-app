package com.hyperactivity.android_app.forum;

import java.util.LinkedList;
import java.util.List;

public class Forum {
	List<Category> categories;
	
	public Forum() {
		this.categories = new LinkedList<Category>();
	}
	
	public List<Category> getCategories() {
		return categories;
	}
}
