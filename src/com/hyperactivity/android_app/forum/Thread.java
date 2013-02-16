package com.hyperactivity.android_app.forum;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hyperactivity.android_app.core.Account;
import com.hyperactivity.android_app.core.Color;
import com.hyperactivity.android_app.core.Post;
import com.hyperactivity.android_app.core.Sort;

public class Thread extends Post {
	String		headline;
	List<Reply>	replies;
	Sort		sort;
	Color		color;

	public Thread(Account author, String headline, String text) {
		this(author, headline, text, Sort.TIME, null, new Date());
	}
	
	public Thread(Account author, String headline, String text, Sort sort, Color color, Date date) {
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
