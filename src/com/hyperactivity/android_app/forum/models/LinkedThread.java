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
public class LinkedThread {
    private int id;
    private String headLine;
    private Category parentPrivateCategory;
    private Account account;

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedThread)) return false;

        LinkedThread l = (LinkedThread) o;

        if (this.getId() != l.getId()) return false;

        if(!Utils.objectsEqual(this.getHeadLine(), l.getHeadLine())) return false;

        if (!Utils.objectsEqual(this.getAccount(), l.getAccount())) return false;

        return true;
    }
}
