package com.hyperactivity.android_app.network;

import com.hyperactivity.android_app.core.Engine;

/*
//TODO: enable the fb stuff
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
*/

public class Network {
    Engine engine;

    public Network(Engine engine) {
        this.engine = engine;
    }

    public void login(final NetworkCallback activityCallBack) {

        /*
        TODO: fix fb stuff
        Response facebookUser = getFacebookUserInfo();
        String email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        String userId = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.EMAIL, email);

        sendRequest(Constants.Methods.LOGIN, userId, params, activityCallBack, true);
        */
    }

    public void register(String username, final NetworkCallback activityCallBack) {

        /*
        TODO: fix fb stuff
        Response facebookUser = getFacebookUserInfo();
        String email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        String userId = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.EMAIL, email);
        params.put(Constants.Transfer.USERNAME, username);

        sendRequest(Constants.Methods.REGISTER, userId, params, activityCallBack, true);
        */
    }
}
