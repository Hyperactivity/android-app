package com.hyperactivity.android_app.forum;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import com.hyperactivity.android_app.core.Color;
import com.hyperactivity.android_app.core.Post;
import com.hyperactivity.android_app.core.Sort;

import android.accounts.Account;

public class Thread extends Post {
	String		headline;
	List<Reply>	replies;
	Sort		sort;
	Color		color;

	public Thread(String headline, Sort sort, Color color, Account author, String text, Date date) {
		super(author, text, date);
		this.headline = headline;
		this.sort = sort;
		this.color = color;
		this.replies = new LinkedList<Reply>();
	}

	public String getHeadline() {
		return headline;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public Sort getSort() {
		return sort;
	}

	public Color getColor() {
		return color;
	}
}
