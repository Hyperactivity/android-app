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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Account;
import com.hyperactivity.android_app.core.Engine;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[]	DUMMY_CREDENTIALS	= new String[] { "foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String		EXTRA_EMAIL			= "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask			mAuthTask			= null;

	// Values for email and password at the time of the login attempt.
	private String					mUsername;
	private String					mPassword;

	// UI references.
	private EditText				mEmailView;
	private EditText				mPasswordView;
	private CheckBox				mAutoLoginBox;
	private View					mLoginFormView;
	private View					mLoginStatusView;
	private TextView				mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		createLoginForm();

		// If autoLogin = true in settings there might be an account stored.
		if (((Engine) getApplication()).getSettings().getAutoLogin()) {
			SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.preferences_file_name), MODE_PRIVATE);

			String username = prefs.getString(getResources().getString(R.string.account_username), null);
			String password = prefs.getString(getResources().getString(R.string.account_password), null);

			if (username != null && password != null) {
				// We have an account stored!
				// Directly login instead of prompting user of username and password.
				attemptLogin(username, password);
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

		((Engine) getApplication()).getSettings().saveLocal();
	}

	private void createLoginForm() {
		// Set up the login form.
		mUsername = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mUsername);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					System.out.println("Stored username and password found. Attempting auto login.");
					attemptLogin();
					return true;
				}
				return false;
			}
		});

		mAutoLoginBox = (CheckBox) findViewById(R.id.auto_login);
		mAutoLoginBox.setChecked(((Engine) getApplication()).getSettings().getAutoLogin());
		mAutoLoginBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((Engine) getApplication()).getSettings().setAutoLogin(isChecked);
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form. If there are form errors (invalid email, missing fields, etc.), the errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		attemptLogin(mEmailView.getText().toString(), mPasswordView.getText().toString());
	}

	public void attemptLogin(String username, String password) {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUsername = username;
		mPassword = password;

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}
		else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mUsername)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}
		else if (!mUsername.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(this);
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});
		}
		else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		private Activity	activity;

		UserLoginTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			}
			catch (InterruptedException e) {
				return false;
			}

			Account account = ((Engine) getApplication()).getServerLink().login(mUsername, mPassword);

			if (account != null && account.isLoaded()) {
				if (((Engine) getApplication()).getSettings().getAutoLogin()) {
					// Save username and password in prefs file.
					SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.preferences_file_name), MODE_PRIVATE);
					Editor editor = prefs.edit();
					editor.putString(getResources().getString(R.string.account_username), mUsername);
					editor.putString(getResources().getString(R.string.account_password), mPassword);
					editor.commit();
				}

				// Save account in engine.
				((Engine) getApplication()).setAccount(account);

				System.out.println("Login success:");
				System.out.println("account id: " + account.getId());
				System.out.println("account username:" + account.getUsername());

				return true;
			}
			else {
				System.err.println("Login failed with error code: " + ((Engine) getApplication()).getServerLink().getErrorCode());
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				activity.startActivity(new Intent(activity, MainActivity.class));
				finish();
			}
			else {
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
