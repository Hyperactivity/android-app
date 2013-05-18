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
public class Note {
    private int id;
    private String headLine;
    private String text;
    private Account account;
    private Category parentPrivateCategory;

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

    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Note)) return false;

        Note n = (Note) o;

        if (this.getId() != n.getId()) return false;

        if(!Utils.objectsEqual(this.getHeadLine(), n.getHeadLine())) return false;

        if (!Utils.objectsEqual(this.getText(), n.getText())) return false;

        if (!Utils.objectsEqual(this.getAccount(), n.getAccount())) return false;

        return true;
    }
}
