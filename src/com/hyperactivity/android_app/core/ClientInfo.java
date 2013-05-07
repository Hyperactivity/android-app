package com.hyperactivity.android_app.core;

import com.hyperactivity.android_app.forum.models.Account;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-05-06
 * Time: 15:41
 */
public class ClientInfo {
    private String facebookToken;
    private Account account;

    public ClientInfo(Account account, String facebookToken) {
        this.account = account;
        this.facebookToken = facebookToken;

    }

    public Account getAccount() {
        return account;
    }

    public String getFacebookToken() {
        return facebookToken;
    }


}
