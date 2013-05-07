package com.hyperactivity.android_app.core;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Build;
import com.hyperactivity.android_app.forum.PrivateForum;
import com.hyperactivity.android_app.forum.PublicForum;
import com.hyperactivity.android_app.network.ServerLink;

public class Engine extends Application {
    boolean loggedIn;
    ClientInfo clientInfo;
    Settings settings;
    ServerLink serverLink;
    int actionCount;
    int timer;
    PublicForum publicForum;
    PrivateForum privateForum;
    boolean publicModeActive;

    // Initialize all data to be needed in Application. Use lazy loading to speed up the app startup process.
    private void initialize() {
        loggedIn = false;
        publicModeActive = true;
        serverLink = new ServerLink(this);
        clientInfo = null;
        settings = new Settings(getApplicationContext());
        actionCount = 0;
        timer = 0;
        publicForum = new PublicForum();
        privateForum = new PrivateForum();
    }

    // Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
    @Override
    public void onCreate() {
        super.onCreate();

        initialize();

        // Read stored settings
        settings.loadLocal();
    }

    // Called by the system when the device configuration changes while you component is running.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory, and would like actively running process to try to tighten their belt.
    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    // This method is for use in emulated process environments.
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    // Called when the operating system has determined that it is a good time for a process to trim unneeded memory from its process.
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

    // Add a new ComponentCallbacks to the base application of the Context, which will be called at the same times as the ComponentCallbacks methods of activities and other components are called.
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

    // Remove a ComponentCallbacks objec that was previously registered with registerComponentCallbacks(ComponentCallbacks).
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        // TODO Auto-generated method stub
        super.unregisterComponentCallbacks(callback);
    }

    public Settings getSettings() {
        return settings;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public PublicForum getPublicForum() {
        return publicForum;
    }

    public PrivateForum getPrivateForum() {
        return privateForum;
    }

    public ServerLink getServerLink() {
        return serverLink;
    }

    public boolean isPublicMode() {
        return publicModeActive;
    }

    public boolean isPrivateMode() {
        return !isPublicMode();
    }

    public void setChangeMode(boolean publicModeActive) {
        this.publicModeActive = publicModeActive;
    }
}
