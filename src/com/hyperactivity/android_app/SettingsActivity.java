package com.hyperactivity.android_app;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * The loader for the menu-item "Settings" for Android versions below 3.0
 * The loader for versions 3.0 and higher is SettingsFragment
 * @author OMMatte
 *
 */
public class SettingsActivity extends PreferenceActivity {
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}