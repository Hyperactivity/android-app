package com.hyperactivity.android_app.activities;

import java.util.List;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.R.xml;

import android.annotation.TargetApi;
import android.os.Build;
import android.preference.PreferenceActivity;

/**
 * The loader for the menu-item headers in "Settings" for Android versions including and above 3.0
 * The loader for versions below 3.0 is SettingsActivity
 * @author OMMatte
 *
 */
public class SettingsActivityHoneycomb extends PreferenceActivity {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.settings_headers, target);
    }
}