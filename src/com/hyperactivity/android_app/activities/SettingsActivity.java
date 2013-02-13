package com.hyperactivity.android_app.activities;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.R.xml;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * The loader for the menu-item "Settings" for Android versions below 3.0
 * The loader for versions 3.0 and higher is SettingsActivityHoneycomb together with SettingsFragment
 * @author OMMatte
 *
 */
public class SettingsActivity extends PreferenceActivity {
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        // Remember to add every time a new header is added to the headers xml
        addPreferencesFromResource(R.xml.mood_colors_settings);
        addPreferencesFromResource(R.xml.miscellaneous_settings);
        
    }
}