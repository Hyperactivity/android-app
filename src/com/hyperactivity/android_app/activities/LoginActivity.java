package com.hyperactivity.android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.ClientInfo;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.Account;
import com.hyperactivity.android_app.network.NetworkCallback;
import net.minidev.json.JSONObject;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class LoginActivity extends FragmentActivity {

    private Engine engine;

    private Fragment registerFragment;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        registerFragments();

        engine = ((Engine) getApplication());
//        start Facebook Login
        Session.openActiveSession(this, true,
                new Session.StatusCallback() {
                    @Override
                    public void call(final Session session,
                                     SessionState state, Exception exception) {
                        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                            // loginCallback after Graph API response with user object
                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                if (user != null) {

                                    loginClicked(session, user);
                                }
                            }

                        });
                    }
                });
    }

    private void registerFragments() {
        FragmentManager fm = getSupportFragmentManager();
        registerFragment = fm.findFragmentById(R.id.register_fragment_id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(registerFragment);
        transaction.commit();
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
                login(account, session);
            }

            @Override
            public void onOther(String status, JSONObject result, int userId) throws Exception {
                if (status.equals(Constants.Transfer.REGISTER)) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.show(registerFragment);
                    transaction.commit();
                } else {
                    //TODO: Unknown status, throw error or something.
                }
            }

            @Override
            public Activity getActivity() {
                return self;
            }
        });
    }

    public void createAccount(View v) {
        TextView usernameView = (TextView) findViewById(R.id.username_textfield);
        final String username = usernameView.getEditableText().toString();
        if (username.length() < Constants.Transfer.MIN_USERNAME || username.length() > Constants.Transfer.MAX_USERNAME) {
            Toast.makeText(this, "Username must be over " + Constants.Transfer.MIN_USERNAME + " and under " + Constants.Transfer.MAX_USERNAME + " characters long. Please try again.", Toast.LENGTH_SHORT).show();
        } else {


            Session.openActiveSession(this, true,
                    new Session.StatusCallback() {
                        @Override
                        public void call(final Session session,
                                         SessionState state, Exception exception) {
                            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                                // loginCallback after Graph API response with user object
                                @Override
                                public void onCompleted(GraphUser user, Response response) {
                                    if (user != null) {
                                        registerClicked(session, user, username);
                                    }
                                }

                            });
                        }
                    });
        }

    }

    private void registerClicked(final Session session, GraphUser user, final String username) {
        final Activity self = this;

        engine.getServerLink().register(session, user, username, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);
                Account account = deSerialize(Account.class, (String) result.get(Constants.Transfer.ACCOUNT));
                login(account, session);
            }

            @Override
            public void onOther(String status, JSONObject result, int userId) throws Exception {
                if (status.equals(Constants.Transfer.USERNAME_TAKEN)) {
                    Toast.makeText(getActivity(), "Username: " + username + " was taken. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO: Unknown status, throw error or something.
                }
            }

            @Override
            public Activity getActivity() {
                return self;
            }
        });
    }
    private void login(Account account, Session session){
        ClientInfo clientInfo = new ClientInfo(account, session.getAccessToken());
        engine.setClientInfo(clientInfo);
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
