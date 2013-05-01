package com.hyperactivity.android_app.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.usb.UsbDevice;
import android.util.Base64;
import android.util.Log;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thoughtworks.xstream.XStream;
import net.minidev.json.JSONObject;

import java.io.*;

public abstract class NetworkCallback {
    private static final XStream X_STREAM = new XStream();

    public void onNetworkTaskComplete(Boolean successful, Object result, int userId) {
        try {
            if (result != null) {
                Log.d(Constants.Log.TAG, "response: " + result.toString());
            }

            if (!successful) {
                if (result != null) {
                    onError((JSONRPC2Error) result, userId);
                } else {
                    onError(userId);
                }
            } else {

                if (result instanceof JSONObject) {

                    JSONObject resultObject = (JSONObject) result;
                    if (resultObject.containsKey(Constants.Transfer.STATUS) && resultObject.get(Constants.Transfer.STATUS).equals(Constants.Transfer.SUCCESS)) {
                        onSuccess(resultObject, userId);
                    } else {
                        onError(resultObject, userId);
                    }
                } else if (result instanceof JSONRPC2Error) {
                    onError((JSONRPC2Error) result, userId);
                } else {
                    onError(userId);
                }
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG, "exception: ", e);
        }
    }

    public void onSuccess(JSONObject result, int userId) throws Exception {
    }

    public void onError(JSONRPC2Error error, int userId) throws Exception {
        showDialog();
    }

    public void onError(JSONObject error, int userId) throws Exception {
        showDialog();
    }

    public void onError(int userId) throws Exception {
        showDialog();
    }

    public void onErrorDismissed() {
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.connection_problems_text)
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

    /**
     * Used to deSerialize an object
     *
     * @param classType
     * @param serializedObject
     * @param <T>              Returns the object as the given class type.
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static final <T> T deSerialize(java.lang.Class<T> classType, String serializedObject) throws ClassNotFoundException, IOException {
        return (T) X_STREAM.fromXML(serializedObject);
    }
}
