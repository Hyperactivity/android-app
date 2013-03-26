package com.hyperactivity.android_app.network;

import android.util.Log;
import com.hyperactivity.android_app.Constants;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 3/25/13
 * Time: 4:29 PM
 */
public abstract class TestNetworkCallback extends NetworkCallback{
    public TestNetworkCallback() {
        Log.w(Constants.Log.TAG, "Warning, TestNetworkCallback is used!");
    }

    /**
     * This method should populate response with test data.
     */
    public abstract void createResponse(JSONObject response);

    public JSONRPC2Response getResponse() {
        JSONObject response = new JSONObject();

        createResponse(response);

        //TODO: fix this!
        return new JSONRPC2Response(response, "Test response");
    }
}
