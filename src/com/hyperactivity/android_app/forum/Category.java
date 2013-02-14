package com.hyperactivity.android_app.forum;

import java.util.LinkedList;
import java.util.List;

import com.hyperactivity.android_app.core.Color;

public class Category {
	String			subject;
	List<Category>	categories;
	List<Thread>	threads;
	Color			color;

	public Category(String subject, Color color) {
		this.subject = subject;
		this.color = color;
		this.categories = new LinkedList<Category>();
		this.threads = new LinkedList<Thread>();
	}

	public List<Category> getCategories() {
		return categories;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public Color getColor() {
		return color;
	}

	public String getSubject() {
		return subject;
	}
}
