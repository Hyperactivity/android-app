package com.hyperactivity.android_app.forum;

public interface ForumEventCallback {
    public void loadingFinished();
    public void loadingStarted();
    public void loadingFailed();

    public void categoriesLoaded();
    public void threadsLoaded();
    public void repliesLoaded();
}
