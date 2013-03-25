package com.hyperactivity.android_app.forum;

import java.util.LinkedList;
import java.util.List;

import com.hyperactivity.android_app.core.Account;
import com.hyperactivity.android_app.core.Color;
import com.hyperactivity.android_app.core.RemoteObject;

public class Category extends RemoteObject{
	String			subject;
	List<Category>	categories;
	List<Thread>	threads;
	Color			color;

	public Category(String id, String subject) {
		this(id, subject, null);
	}
	
	public Category(String id, String subject, Color color) {
		super(id);
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

	public boolean isColored() {
		return color != null;
	}
	
	public Color getColor() {
		return color;
	}

	public String getSubject() {
		return subject;
	}
	
	public void addThread(Thread thread) {
		if(!threads.contains(thread)) {
			threads.add(thread);
		}
	}
	
	public void createThread(Account author, String headline, String text) {
		addThread(new Thread(author, headline, text));
	}
}
