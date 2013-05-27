package com.hyperactivity.android_app.forum;

import com.hyperactivity.android_app.forum.models.Reply;
import com.hyperactivity.android_app.forum.models.Thread;

public interface ForumEventCallback {
    public void loadingFinished();
    public void loadingStarted();
    public void loadingFailed();

    public void categoriesLoaded();
    public void threadsLoaded();
    public void repliesLoaded();
    public void notesLoaded();

    public void threadCreated(Thread thread);
    public void replyCreated(Reply reply);
}
