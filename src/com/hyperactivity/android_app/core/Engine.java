package com.hyperactivity.android_app.core;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;

public class Engine extends Application{
	private boolean loggedIn;
	private Account account;
	private Settings settings;
	private int actionCount;
	private int timer;
	
	//Initialize all data to be needed in Application. Use lazy loading to speed up the app startup process.
	private void initialize() {
		loggedIn = false;
		account = null;
		settings = new Settings(getApplicationContext());
		actionCount = 0;
		timer = 0;
	}
	
	//Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
	@Override
	public void onCreate() {
		super.onCreate();
		
		initialize();
		
		//Read stored settings
		settings.loadLocal();
		
	}
	
	// Called by the system when the device configuration changes while you component is running.
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	//This is called when the overall system is running low on memory, and would like actively running process to try to tighten their belt.
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	//This method is for use in emulated process environments.
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	//Called when the operating system has determined that it is a good time for a process to trim unneeded memory from its process.
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void registerActivityLifecycleCallbacks(
			ActivityLifecycleCallbacks callback) {
		// TODO Auto-generated method stub
		super.registerActivityLifecycleCallbacks(callback);
	}

	//Add a new ComponentCallbacks to the base application of the Context, which will be called at the same times as the ComponentCallbacks methods of activities and other components are called.
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void registerComponentCallbacks(ComponentCallbacks callback) {
		// TODO Auto-generated method stub
		super.registerComponentCallbacks(callback);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void unregisterActivityLifecycleCallbacks(
			ActivityLifecycleCallbacks callback) {
		// TODO Auto-generated method stub
		super.unregisterActivityLifecycleCallbacks(callback);
	}

	//Remove a ComponentCallbacks objec that was previously registered with registerComponentCallbacks(ComponentCallbacks).
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void unregisterComponentCallbacks(ComponentCallbacks callback) {
		// TODO Auto-generated method stub
		super.unregisterComponentCallbacks(callback);
	}
	
	public Settings getSettings() {
		return settings;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
}
