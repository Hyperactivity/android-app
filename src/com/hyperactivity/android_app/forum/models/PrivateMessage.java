package com.hyperactivity.android_app.forum.models;

import com.hyperactivity.android_app.Utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class PrivateMessage {
    private String text;
    private Account senderAccount;
    private Account recieverAccount;
    private Timestamp time;

    public Account getSenderAccount() {
        return senderAccount;
    }

    public Account getRecieverAccount() {
        return recieverAccount;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PrivateMessage)) return false;

        PrivateMessage p = (PrivateMessage) o;

        if (!Utils.objectsEqual(this.getText(), p.getText())) return false;

        if (!Utils.objectsEqual(this.getSenderAccount(), p.getSenderAccount())) return false;

        if (!Utils.objectsEqual(this.getRecieverAccount(), p.getRecieverAccount())) return false;

        if (!Utils.objectsEqual(this.getTime(), p.getTime())) return false;

        return true;
    }
}
