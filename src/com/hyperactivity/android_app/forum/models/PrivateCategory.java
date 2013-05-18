package com.hyperactivity.android_app.forum.models;


import com.hyperactivity.android_app.Utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class PrivateCategory {
    private int id;
    private int colorCode;
    private Category parentPrivateCategory;
    private Account account;
    private String headLine;
    private List<Note> notes;
    private List<Thread> linkedThreads;

    public String getHeadLine() {
        return headLine;
    }

    public int getId() {
        return id;
    }

    public int getColorCode() {
        return colorCode;
    }

    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PrivateCategory)) return false;

        PrivateCategory p = (PrivateCategory) o;

        if (this.getId() != p.getId()) return false;

        if (this.getColorCode() != p.getColorCode()) return false;

        if (!Utils.objectsEqual(this.getAccount(), p.getAccount())) return false;

        if(!Utils.objectsEqual(this.getHeadLine(), p.getHeadLine())) return false;

        return true;
    }
}
