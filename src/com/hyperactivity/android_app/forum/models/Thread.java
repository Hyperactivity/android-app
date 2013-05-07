package com.hyperactivity.android_app.forum.models;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
public class Thread {
    private int id;
    private String headLine;
    private String text;
    private Account account;
    private Category parentCategory;
    private Timestamp time;
    private List<Reply> replies;

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getText() {
        return text;
    }

    public Account getAccount() {
        return account;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<Reply> getReplies() {
        if (replies == null) {
            replies = new LinkedList<Reply>();
        }

        return replies;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Thread)) {
            return false;
        }

        Thread t = (Thread) o;

        if (!this.getHeadLine().equals(t.getHeadLine())) {
            return false;
        }

        if (!this.getReplies().equals(t.getReplies())) {
            return false;
        }

        if (!this.getAccount().equals(t.getAccount())) {
            return false;
        }

        if (this.getId() != t.getId()) {
            return false;
        }

        if (!this.getParentCategory().equals(t.getParentCategory())) {
            return false;
        }

        if (!this.getText().equals(t.getText())) {
            return false;
        }

        if (!this.getTime().equals(t.getTime())) {
            return false;
        }

        return true;
    }
}
