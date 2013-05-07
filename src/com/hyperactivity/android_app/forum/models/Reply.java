package com.hyperactivity.android_app.forum.models;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.*;
import java.sql.Timestamp;
import java.util.List;

import com.hyperactivity.android_app.forum.models.Thread;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
public class Reply {
    private int id;
    private Timestamp time;
    private String text;
    private Thread parentThread;
    private Account account;
    private List<ThumbsUp> thumbsUp;

    public int getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public Thread getParentThread() {
        return parentThread;
    }

    public Account getAccount() {
        return account;
    }

    public List<ThumbsUp> getThumbsUp() {
        return thumbsUp;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Reply)) {
            return false;
        }

        Reply r = (Reply)o;

        if(!this.getTime().equals(r.getTime())) {
            return false;
        }

        if(!this.getText().equals(r.getText())) {
            return false;
        }

        if(this.getId() != r.getId())
        {
            return false;
        }

        if(!this.getAccount().equals(r.getAccount())) {
            return false;
        }

        if(!this.getParentThread().equals(r.getParentThread()))
        {
            return false;
        }

        if(!this.getThumbsUp().equals(r.getThumbsUp())) {
            return false;
        }

        return true;
    }
}
