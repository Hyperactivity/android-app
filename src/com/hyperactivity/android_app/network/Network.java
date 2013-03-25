package com.hyperactivity.android_app.network;

import android.os.AsyncTask;
import android.util.Log;
/*
//TODO: enable the fb stuff
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
*/
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.hyperactivity.android_app.core.Engine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-12
 * Time: 18:08
 */
public class Network {
    Engine engine;

    public Network(Engine engine) {
        this.engine = engine;
    }

    public void login(final NetworkCallback activityCallBack) {

        Response facebookUser = getFacebookUserInfo();
        String email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        String userId = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.EMAIL, email);

        sendRequest(Constants.Methods.LOGIN, userId, params, activityCallBack, true);
    }

    public void register(String username, final NetworkCallback activityCallBack) {
        Response facebookUser = getFacebookUserInfo();
        String email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        String userId = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.EMAIL, email);
        params.put(Constants.Transfer.USERNAME, username);

        sendRequest(Constants.Methods.REGISTER, userId, params, activityCallBack, true);
    }

    public void getFriends(final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        Response facebookFriends = getFacebookFriendsInfo();
        JSONObject facebookFriendsJson = facebookFriends.getGraphObject().getInnerJSONObject();
        JSONArray friendsArrayWithName = null;
        try {
            friendsArrayWithName = facebookFriendsJson.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //TODO: You have no friends?
        }

        net.minidev.json.JSONArray friendsArrayWithoutName = new net.minidev.json.JSONArray();
        for (int i = 0; i < friendsArrayWithName.length(); i++) {
            JSONObject row = null;
            try {
                row = friendsArrayWithName.getJSONObject(i);
                friendsArrayWithoutName.add(row.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        sendRequest(Constants.Methods.GET_FRIENDS, engine.getUserID(), friendsArrayWithoutName, activityCallback, lockWithLoadingScreen);
    }

    public void getGameObjects(String friendID, final NetworkCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put(Constants.Params.FRIEND_ID, friendID);
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception", e);
        }

        sendRequest(Constants.Methods.GET_GAME_OBJECTS, engine.getUserID(), params, callback, true);
    }

    public void sendRaceDone(String gameID, boolean crashed, final NetworkCallback callback, boolean showDialog) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put(Constants.Transfer.GAME_ID, gameID);

            if (crashed) {
                params.put(Constants.Params.CRASHED, "true");
            } else {
                params.put(Constants.Params.CRASHED, "false");
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception", e);
        }

        sendRequest(Constants.Methods.SEND_RACE_DONE, engine.getUserID(), params, callback, showDialog);
    }

    public void placeObstacle(String friendID, Obstacle obstacle, final NetworkCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put(Constants.Params.FRIEND_ID, friendID);
            params.put(Constants.Params.OBSTACLE, obstacle.toJSON());
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception", e);
        }

        sendRequest(Constants.Methods.PLACE_OBSTACLE, engine.getUserID(), params, callback, true);
    }

    public void requestNewGame(String friendID, final NetworkCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put(Constants.Params.FRIEND_ID, friendID);
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception", e);
        }

        sendRequest(Constants.Methods.REQUEST_NEW_GAME, engine.getUserID(), params, callback, true);
    }

    public void acceptInvite(String friendID, final NetworkCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();

        try {
            params.put(Constants.Params.FRIEND_ID, friendID);
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception", e);
        }

        sendRequest(Constants.Methods.ACCEPT_INVITE, engine.getUserID(), params, callback, true);
    }

    public void declineInvite(String friendID, final NetworkCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();

        try {
            params.put(Constants.Params.FRIEND_ID, friendID);
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception", e);
        }

        sendRequest(Constants.Methods.DECLINE_INVITE, engine.getUserID(), params, callback, true);
    }

    private void sendRequest(String method, String id, List<Object> params, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        final JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);
        sendRequest(reqOut, activityCallback, lockWithLoadingScreen);
    }

    private void sendRequest(String method, String id, java.util.Map<String, Object> params, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        // Prepare request
        final JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);
        sendRequest(reqOut, activityCallback, lockWithLoadingScreen);
    }

    private void sendRequest(JSONRPC2Request request, final NetworkCallback activityCallback, boolean lockWithLoadingScreen) {
        try {
            AsyncTask asyncTask = new NetworkAsyncTask(activityCallback, lockWithLoadingScreen);
            asyncTask.execute(request);
        }
        catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception: ", e);
        }
    }

    private Response getFacebookUserInfo() {
        Session session = Session.getActiveSession();

        Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {

            /**
             * The method that will be called when the request completes.
             *
             * @param user     the GraphObject representing the returned user, or null
             * @param response the Response of this request, which may include error information if the request was unsuccessful
             */
            @Override
            public void onCompleted(GraphUser user, Response response) {
            }
        });
        return request.executeAndWait();
    }

    private Response getFacebookFriendsInfo() {
        Session session = Session.getActiveSession();

        Request request = Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
            /**
             * The method that will be called when the request completes.
             *
             * @param user     the GraphObject representing the returned user, or null
             * @param response the Response of this request, which may include error information if the request was unsuccessful
             */
            @Override
            public void onCompleted(List<GraphUser> user, Response response) {
            }
        });
        return request.executeAndWait();
    }
}
