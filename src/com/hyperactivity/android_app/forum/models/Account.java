package com.hyperactivity.android_app.forum.models;

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

        if (this.getId() != a.getId()) {
            return false;
        }

        if (!(this.getBirthDate() == null && a.getBirthDate() == null || this.getBirthDate().equals(a.getBirthDate()))) {
            return false;
        }

        if (this.getFacebookId() != a.getFacebookId()) {
            return false;
        }

        if (this.getLimitPerDay() != a.getLimitPerDay()) {
            return false;
        }

        if (!this.getProfileDescription().equals(a.getProfileDescription())) {
            return false;
        }

        if (!(this.username == null && a.getUsername() == null || this.getUsername().equals(a.getUsername()))) {
            return false;
        }

        if (!(this.admin == a.admin)) {
            return false;
        }

        return true;
    }
}
