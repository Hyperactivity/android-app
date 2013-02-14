package com.hyperactivity.android_app.forum;

import java.sql.Date;

import com.hyperactivity.android_app.core.Post;

import android.accounts.Account;

public class Reply extends Post {
	int	relevance;
	int	order;

	public Reply(int relevance, int order, Account author, String text, Date date) {
		super(author, text, date);
		this.relevance = relevance;
		this.order = order;
	}

	public int getRelevance() {
		return relevance;
	}

	public int getOrder() {
		return order;
	}
}
