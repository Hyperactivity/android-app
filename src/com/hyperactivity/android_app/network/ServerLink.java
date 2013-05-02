package com.hyperactivity.android_app.network;

import android.graphics.Bitmap;
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

    //---------------------------- ACCOUNT ----------------------------

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

    public void getAccount(int accountID, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.ACCOUNT_ID, accountID);

        sendRequest(Constants.Methods.GET_ACCOUNT, params, callback, lockWithLoadingScreen);
    }

    public void updateAccount(String description, boolean showBirthDate, Bitmap avatar, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.DESCRIPTION, description);
        params.put(Constants.Transfer.SHOW_BIRTHDATE, showBirthDate);

        //TODO: Also send avatar.

        sendRequest(Constants.Methods.UPDATE_PROFILE, params, callback, lockWithLoadingScreen);
    }

    //---------------------------- CATEGORY ----------------------------

    public void getForumContent(String type, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.TYPE, type);

        sendRequest(Constants.Methods.GET_FORUM_CONTENT, params, callback, lockWithLoadingScreen);
    }

    public void getCategoryContent(int categoryID, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.CATEGORY_ID, categoryID);

        sendRequest(Constants.Methods.GET_CATEGORY_CONTENT, params, callback, lockWithLoadingScreen);
    }

    public void createCategory(String type, String headline, int colorCode, boolean lockWithLoadinScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.TYPE, type);
        params.put(Constants.Transfer.HEADLINE, headline);
        params.put(Constants.Transfer.COLOR_CODE, colorCode);

        sendRequest(Constants.Methods.CREATE_CATEGORY, params, callback, lockWithLoadinScreen);
    }

    public void modifyCategory(int categoryID, String type, String headline, int colorCode, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.CATEGORY_ID, categoryID);
        params.put(Constants.Transfer.TYPE, type);
        params.put(Constants.Transfer.HEADLINE, headline);
        params.put(Constants.Transfer.COLOR_CODE, colorCode);

        sendRequest(Constants.Methods.MODIFY_CATEGORY, params, callback, lockWithLoadingScreen);
    }

    public void deleteCategory(int categoryID, String type, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.CATEGORY_ID, categoryID);
        params.put(Constants.Transfer.TYPE, type);

        sendRequest(Constants.Methods.DELETE_CATEGORY, params, callback, lockWithLoadingScreen);
    }

    //---------------------------- THREAD ----------------------------

    public void getLatestThreads(int limit, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.LIMIT, limit);

        sendRequest(Constants.Methods.GET_LATEST_THREADS, params, callback, lockWithLoadingScreen);
    }

    public void getThreadContent(int threadID, int sortType, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.THREAD_ID, threadID);
        params.put(Constants.Transfer.SORT_TYPE, sortType);

        sendRequest(Constants.Methods.GET_THREAD_CONTENT, params, callback, lockWithLoadingScreen);
    }

    public void createThread(int categoryID, String headline, String text, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.CATEGORY_ID, categoryID);
        params.put(Constants.Transfer.HEADLINE, headline);
        params.put(Constants.Transfer.TEXT, text);

        sendRequest(Constants.Methods.CREATE_THREAD, params, callback, lockWithLoadingScreen);
    }

    public void modifyThread(int threadID, String headline, String text, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.THREAD_ID, threadID);
        params.put(Constants.Transfer.HEADLINE, headline);
        params.put(Constants.Transfer.TEXT, text);

        sendRequest(Constants.Methods.MODIFY_THREAD, params, callback, lockWithLoadingScreen);
    }

    public void deleteThread(int threadID, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.THREAD_ID, threadID);

        sendRequest(Constants.Methods.DELETE_THREAD, params, callback, lockWithLoadingScreen);
    }

    //---------------------------- REPLIES ----------------------------

    public void createReply(int threadID, String text, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.THREAD_ID, threadID);
        params.put(Constants.Transfer.TEXT, text);

        sendRequest(Constants.Methods.CREATE_REPLY, params, callback, lockWithLoadingScreen);
    }

    public void modifyReply(int replyID, String text, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.REPLY_ID, replyID);
        params.put(Constants.Transfer.TEXT, text);

        sendRequest(Constants.Methods.MODIFY_REPLY, params, callback, lockWithLoadingScreen);
    }

    public void deleteReply(int replyID, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.REPLY_ID, replyID);

        sendRequest(Constants.Methods.DELETE_REPLY, params, callback, lockWithLoadingScreen);
    }

    public void thumbUp(int replyID, boolean lockWithLoadingScreen, final NetworkCallback callback) {
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.REPLY_ID, replyID);

        sendRequest(Constants.Transfer.THUMB_UP, params, callback, lockWithLoadingScreen);
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
