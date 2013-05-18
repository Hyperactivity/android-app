package com.hyperactivity.android_app.forum.models;


import com.hyperactivity.android_app.Utils;

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

        if (!Utils.objectsEqual(this.getHeadLine(), t.getHeadLine())) return false;

        if (!Utils.objectsEqual(this.getAccount(), t.getAccount())) return false;

        if (this.getId() != t.getId()) return false;

        if (!Utils.objectsEqual(this.getText(), t.getText())) return false;

        if (!Utils.objectsEqual(this.getTime(), t.getTime())) return false;

        return true;
    }
}
