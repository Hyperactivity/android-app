package com.hyperactivity.android_app.network;

import android.os.AsyncTask;
import android.util.Log;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.core.Engine;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import java.util.HashMap;
import java.util.List;

/*
//TODO: enable the fb stuff
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
*/

public class ServerLink {
    Engine engine;

    public ServerLink(Engine engine) {
        this.engine = engine;
    }

    public void login(final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        String facebookID = "TODO";

        /*
        TODO: fix fb stuff
        Response facebookUser = getFacebookUserInfo();
        facebookID = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        */

        sendRequest(Constants.Methods.LOGIN, facebookID, params, callback, true);
    }

    public void getProfile(String accountID, NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.Transfer.ACCOUNT_ID, accountID);

        sendRequest(Constants.Methods.GET_PROFILE, params, callback, true);
    }

    public void getForum(String type, NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.Transfer.TYPE, type);

        sendRequest(Constants.Methods.GET_FORUM, params, callback, true);
    }

    public void getCategoryContent(String categoryID, NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.Transfer.CATEGORY_ID, categoryID);

        sendRequest(Constants.Methods.GET_CATEGORY_CONTENT, params, callback, true);
    }

    public void getThread(String threadID, NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.Transfer.THREAD_ID, threadID);

        sendRequest(Constants.Methods.GET_THREAD, params, callback, true);
    }

    public void createThread(String categoryID, String headline, String text, NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.Transfer.CATEGORY_ID, categoryID);
        params.put(Constants.Transfer.HEADLINE, headline);
        params.put(Constants.Transfer.TEXT, text);

        sendRequest(Constants.Methods.CREATE_THREAD, params, callback, true);
    }

    public void createReply(String threadID, String text, NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.Transfer.THREAD_ID, threadID);
        params.put(Constants.Transfer.TEXT, text);

        sendRequest(Constants.Methods.CREATE_REPLY, params, callback, true);
    }

    private void sendRequest(String method, String id, List<Object> params, final NetworkCallback callback, boolean lockWithLoadingScreen) {
        final JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);
        sendRequest(reqOut, callback, lockWithLoadingScreen);
    }

    private void sendRequest(String method, java.util.Map<String, Object> params, final NetworkCallback callback, boolean lockWithLoadingScreen) {
        if (engine.getAccount() != null) {
            sendRequest(method, engine.getAccount().getId(), params, callback, lockWithLoadingScreen);

        } else {
            callback.onError("TODO"); //TODO: handle message better. maybe create json error object
        }
    }

    private void sendRequest(String method, String id, java.util.Map<String, Object> params, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        final JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);
        sendRequest(reqOut, activityCallback, lockWithLoadingScreen);
    }

    private void sendRequest(JSONRPC2Request request, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        try {
            AsyncTask asyncTask = new NetworkAsyncTask(activityCallback, lockWithLoadingScreen);
            asyncTask.execute(request);
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception: ", e);
        }
    }

    /*
    TODO: implement fb stuff
    private Response getFacebookUserInfo() {
        Session session = Session.getActiveSession();

        Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {

            @Override
            public void onCompleted(GraphUser user, Response response) {
            }
        });
        return request.executeAndWait();
    }
    */
}
