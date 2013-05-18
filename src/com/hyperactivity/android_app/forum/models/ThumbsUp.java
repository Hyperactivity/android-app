package com.hyperactivity.android_app.forum.models;


import com.hyperactivity.android_app.Utils;

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
        if (!(o instanceof ThumbsUp)) return false;

        ThumbsUp t = (ThumbsUp) o;

        if (!Utils.objectsEqual(this.getAccount(), t.getAccount())) return false;

        if (!Utils.objectsEqual(this.getReply(), t.getReply())) return false;

        return true;
    }
}
