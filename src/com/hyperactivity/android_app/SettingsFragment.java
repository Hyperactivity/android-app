package com.hyperactivity.android_app;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
/**
 * The loader for the menu-items "Settings" for Android versions including and above 3.0
 * The loader for versions below 3.0 is SettingsActivity
 * @author OMMatte
 *
 */
public class SettingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		// Gets the value from the active head in the headers xml
		int res=getActivity().getResources().getIdentifier(getArguments().getString("resource"), "xml", getActivity().getPackageName());
	    addPreferencesFromResource(res);
	}
}