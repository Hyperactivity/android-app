package com.hyperactivity.android_app.forum.models;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class ThumbsUp {
    private Reply reply;
    private Account account;

    public Reply getReply() {
        return reply;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ThumbsUp)) {
            return false;
        }

        ThumbsUp t = (ThumbsUp) o;

        if (this.getAccount().equals(t.getAccount())) {
            return false;
        }

        if (this.getReply().equals(t.getReply())) {
            return false;
        }

        return true;
    }
}
