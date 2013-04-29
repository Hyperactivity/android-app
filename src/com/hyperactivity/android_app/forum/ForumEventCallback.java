package com.hyperactivity.android_app.forum;

public interface ForumEventCallback {
    public abstract void loadingFinished();
    public abstract void loadingStarted();
    public abstract void loadingFailed();
}
