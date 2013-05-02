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
        String email = "TODO";
        int facebookID = 1337;

        /*
        TODO: fix fb stuff
        Response facebookUser = getFacebookUserInfo();
        email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        facebookID = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        */

        params.put(Constants.Transfer.EMAIL, email);
        sendRequest(Constants.Methods.LOGIN, facebookID, params, callback, true);
    }

    public void register(String username, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        String email = "TODO";
        int facebookID = 1337;

        //TODO: should this method method auth with fb-email or fid (facebook id)?

        /*
        TODO: fix fb stuff
        Response facebookUser = getFacebookUserInfo();
        email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        facebookID = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        */

        params.put(Constants.Transfer.EMAIL, email);
        params.put(Constants.Transfer.USERNAME, username);

        sendRequest(Constants.Methods.REGISTER, facebookID, params, callback, true);
    }

    public void getForumContent(String type, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.TYPE, type);

        sendRequest(Constants.Methods.GET_FORUM_CONTENT, params, callback, true);
    }

    public void getCategoryContent(int categoryID, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.CATEGORY_ID, categoryID);

        sendRequest(Constants.Methods.GET_CATEGORY_CONTENT, params, callback, true);
    }

    //---------------------------- HELPER METHODS ----------------------------

    private void sendRequest(String method, String id, List<Object> params, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        final JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);
        sendRequest(reqOut, activityCallback, lockWithLoadingScreen);
    }

    private void sendRequest(String method, java.util.Map<String, Object> params, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        sendRequest(method, engine.getAccount().getId(), params, activityCallback, lockWithLoadingScreen);
    }

    private void sendRequest(String method, int id, java.util.Map<String, Object> params, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
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
