package com.intnet.space_escape.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import com.intnet.space_escape.R;
import com.intnet.space_escape.assistant.Constants;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import net.minidev.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-15
 * Time: 12:42
 */
public abstract class NetworkCallback {

    public void onNetworkTaskComplete(Boolean successful, Object result, String userId) {
        try {
            if (result != null) {
                Log.d(Constants.Log.TAG, "response: " + result.toString());
            }

            if (!successful) {
                if (result != null) {
                    onError((JSONRPC2Error)result, userId);
                } else {
                    onError(userId);
                }
            } else {
                if (successful && ((JSONObject) result).get(Constants.Transfer.VALUE).equals(Constants.Responses.FAILED)) {
                    onError((JSONObject) result, userId);
                } else {
                    onSuccess(result, userId);
                }
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception: ", e);
        }
    }

    public void onSuccess(Object result, String userId) throws Exception {
    }

    public void onError(JSONRPC2Error error, String userId) throws Exception {
        showDialog();
    }

    public void onError(JSONObject error, String userId) throws Exception {
        showDialog();
    }

    public void onError(String userId) throws Exception {
        showDialog();
    }

    public void onErrorDismissed() {
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.connection_problems)
                .setTitle(R.string.connection_problems_caption)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        onErrorDismissed();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    public abstract Activity getActivity();
}
