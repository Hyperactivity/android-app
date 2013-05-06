package com.hyperactivity.android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.TextView;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.LoginAccount;
import com.hyperactivity.android_app.forum.models.Account;
import com.hyperactivity.android_app.network.NetworkCallback;
import com.hyperactivity.android_app.network.TestNetworkCallback;
import net.minidev.json.JSONObject;

import com.facebook.*;
import com.facebook.model.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class LoginActivity extends Activity {

    private Engine engine;
    private Session.StatusCallback callback =
            new Session.StatusCallback() {
                @Override
                public void call(final Session session,
                                 SessionState state, Exception exception) {
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                loginClicked(session, user);
                            }
                        }
                    });
                }
            };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

        engine = ((Engine)getApplication());
        // start Facebook Login
        Session.openActiveSession(this, true, callback);
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

    public void loginClicked(final Session session, GraphUser user) {
        final Activity self = this;

        engine.getServerLink().login(session, user, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);
                Account account = deSerialize(Account.class, (String) result.get(Constants.Transfer.ACCOUNT));
                LoginAccount loginAccount = new LoginAccount(account, session.getAccessToken());
                engine.setAccount(loginAccount);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }

            @Override
            public Activity getActivity() {
                return self;
            }
        });
    }
}
