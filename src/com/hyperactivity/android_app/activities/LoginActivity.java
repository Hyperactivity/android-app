package com.hyperactivity.android_app.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Account;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.network.NetworkCallback;
import com.hyperactivity.android_app.network.TestNetworkCallback;
import net.minidev.json.JSONObject;

import java.util.Map;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class LoginActivity extends Activity {
    private Engine engine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

        engine = ((Engine)getApplication());
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

    public void loginClicked(View view) {
        final Activity self = this;

        engine.getServerLink().login(new TestNetworkCallback() {
            @Override
            public void onSuccess(Object result, String userId) throws Exception {
                super.onSuccess(result, userId);

                Log.i(Constants.Log.TAG, "hej");

                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }

            @Override
            public Activity getActivity() {
                return self;
            }

            /**
             * This method should populate response with test data.
             */
            @Override
            public void createResponse(JSONObject response) {
                response.put(Constants.Transfer.VALUE, Constants.Transfer.SUCCESS);
            }
        });
    }
}
