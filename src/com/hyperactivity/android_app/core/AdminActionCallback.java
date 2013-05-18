package com.hyperactivity.android_app.core;

import com.hyperactivity.android_app.forum.models.Account;
import com.hyperactivity.android_app.forum.models.Reply;
import com.hyperactivity.android_app.forum.models.Thread;

public interface AdminActionCallback {
    public void editThread(Thread thread);

    public void deleteThread(Thread thread);

    public void editReply(Reply reply);

    public void deleteReply(Reply reply);

    public void banAccount(Account account);
}
