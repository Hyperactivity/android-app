package com.hyperactivity.android_app.core;

import com.hyperactivity.android_app.forum.models.Account;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-05-06
 * Time: 15:41
 */
public class LoginAccount extends Account {
    String facebookToken;

    public LoginAccount(Account account, String facebookToken) {
        super(account);
        this.facebookToken = facebookToken;

    }

    public String getFacebookToken() {
        return facebookToken;
    }

}
