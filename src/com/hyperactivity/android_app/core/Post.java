package com.hyperactivity.android_app.core;

import java.sql.Date;

import android.accounts.Account;

public class Post {
	Account	author;
	String	text;
	Date	date;

	public Post(Account author, String text, Date date) {
		this.author = author;
		this.text = text;
		this.date = date;
	}

	public Account getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}
}
