package com.hyperactivity.android_app.forum.models;

import com.facebook.android.Util;
import com.hyperactivity.android_app.Utils;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class Account {
    private String profileDescription;
    private int id;
    private Date birthDate;
    private int limitPerDay;
    private boolean useDefaultColors;
    private int facebookId;
    private String username;
    private boolean showBirthDate;
    private boolean admin;

    public int getId() {
        return id;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getLimitPerDay() {
        return limitPerDay;
    }

    public boolean isUseDefaultColors() {
        return useDefaultColors;
    }

    public int getFacebookId() {
        return facebookId;
    }

    public String getUsername() {
        return username;
    }

    public boolean isShowBirthDate() {
        return showBirthDate;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account)) {
            return false;
        }

        Account a = (Account) o;

        if (!Utils.objectsEqual(this.getProfileDescription(), a.getProfileDescription())) return false;

        if (this.getId() != a.getId()) return false;

        if (!Utils.objectsEqual(this.getBirthDate(), a.getBirthDate())) return false;

        if (this.getLimitPerDay() != a.getLimitPerDay()) return false;

        if (this.isUseDefaultColors() != a.isUseDefaultColors()) return false;

        if (this.getFacebookId() != a.getFacebookId()) return false;

        if (!Utils.objectsEqual(this.getUsername(), a.getUsername())) return false;

        if (this.isShowBirthDate() != a.isShowBirthDate()) return false;

        if (this.isAdmin() != a.isAdmin()) return false;

        return true;
    }
}
