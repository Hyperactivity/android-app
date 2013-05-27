package com.hyperactivity.android_app.forum.models;

import com.hyperactivity.android_app.Utils;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-05-26
 * Time: 17:05
 */
public class Shout {
    private int id;
    private Timestamp time;
    private String text;
    private Account account;

    public int getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Shout)) {
            return false;
        }

        Shout a = (Shout) o;


        if (!Utils.objectsEqual(this.getTime(), a.getTime())) return false;

        if (this.getId() != a.getId()) return false;

        if (!Utils.objectsEqual(this.getText(), a.getText())) return false;

        if (!Utils.objectsEqual(this.getAccount(), a.getAccount())) return false;

        return true;
    }


}
