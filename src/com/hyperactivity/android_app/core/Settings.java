package com.hyperactivity.android_app.core;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hyperactivity.android_app.R;

public class Settings extends RemoteObject {
	private Context				context;
	private ArrayList<Color>	colors;
	private boolean				changed;	// true if any changes have been made since last commit.

	// settings variables below
	private boolean				autoLogin;

	public Settings(Context context) {
		this(context, -1);
	}

	public Settings(Context context, int id) {
		super(id);
		this.context = context;
		changed = false;
		colors = new ArrayList<Color>();
	}

	/**
	 * Load settings from android device.
	 * If no settings are found the default settings gets loaded.
	 **/
	public void loadLocal() {
		setLoaded(false); // TODO: how does android handle synchronization? This will do for now.

		SharedPreferences prefs = context.getSharedPreferences(context.getResources().getString(R.string.preferences_file_name), Context.MODE_PRIVATE);

		// Read all settings.
		autoLogin = prefs.getBoolean(context.getResources().getString(R.string.settings_auto_login), context.getResources().getBoolean(R.bool.settings_auto_login_default_value));

		super.load();
	}

	/**
	 * Load settings from server.
	 * If no settings are found the default settings gets loaded.
	 **/
	public void loadRemote() {
		setLoaded(false); // TODO: how does android handle synchronization? This will do for now.

		if (getId() > 0) {
			// Load from server
			super.load();
		}
		else {
			// Load default
			loadDefault();
		}
	}

	/**
	 * Load settings from server.
	 * If no settings are found the default settings gets loaded.
	 **/
	public void loadDefault() {
		setLoaded(false); // TODO: how does android handle synchronization? This will do for now.

		autoLogin = context.getResources().getBoolean(R.bool.settings_auto_login_default_value);

		super.load();
	}

	/**
	 * Save current settings to local android device
	 */
	public void saveLocal() {
		if (changed) {
			SharedPreferences prefs = context.getSharedPreferences(context.getResources().getString(R.string.preferences_file_name), Context.MODE_PRIVATE);
			Editor editor = prefs.edit();

			editor.putBoolean(context.getResources().getString(R.string.settings_auto_login), autoLogin);

			editor.commit();

			changed = false;
		}
	}

	/**
	 * Save current settings to user on remote server
	 */
	public void saveRemote() {

	}

	public Color getColor(int index) {
		return colors.get(index);
	}

	public boolean addColor(Color color) {
		if (colors.contains(color)) {
			return false;
		}

		colors.add(color);
		return true;
	}

	public boolean removeColor(Color color) {
		if (!colors.contains(color)) {
			return false;
		}

		colors.remove(color);
		return true;
	}

	public int numColors() {
		return colors.size();
	}

	// Settings below

	public boolean getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		setValue();
		this.autoLogin = autoLogin;
	}

	private void setValue() {
		changed = true;
	}
}
