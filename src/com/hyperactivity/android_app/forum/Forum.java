package com.hyperactivity.android_app.forum;

import android.app.Activity;
import android.util.Log;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.activities.MainActivity;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.*;
import com.hyperactivity.android_app.forum.models.Thread;
import com.hyperactivity.android_app.network.NetworkCallback;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import net.minidev.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Forum {
    private ForumType type;
    private List<Category> categories;
    private ForumEventCallback callback;
    private List<Thread> latestThreads;

    public Forum(ForumType type) {
        this(type, null);
    }

    public Forum(ForumType type, ForumEventCallback callback) {
        this.type = type;
        this.callback = callback;
        this.categories = new LinkedList<Category>();
        this.latestThreads = new LinkedList<Thread>();
    }

    public void setCallback(ForumEventCallback callback) {
        this.callback = callback;
    }

    public List<Category> getCategories() {
        return categories;
    }


    public List<Category> getCategories(Activity activity) {
        loadCategories(activity, false);

        return getCategories();
    }

    public List<Thread> getLatestThreads() {
        return latestThreads;
    }


    public List<Thread> getLatestThreads(Activity activity, int limit) {
        loadLatestThreads(activity, limit, false);

        return getLatestThreads();
    }

    public List<Thread> getThreads(Activity activity, Category category) {
        loadThreads(activity, category, false);

        return category.getThreads();
    }

    public List<Note> getNotes(Activity activity, PrivateCategory category) {
        loadNotes(activity, category, false);

        return category.getNotes();
    }

    @SuppressWarnings("unchecked")
    public void loadCategories(final Activity activity, boolean lockWithLoadingScreen) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().getForumContent(type.toString(), lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                boolean newData = false;

                try {
                    List<Category> resultCategories = (LinkedList<Category>) deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.CATEGORIES));

                    if (!categories.equals(resultCategories)) {
                        categories = resultCategories;
                        newData = true;
                    }
                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, "exception", e);
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();

                if (newData) {
                    callback.categoriesLoaded();
                    Log.d(Constants.Log.TAG, "New categories");
                }
            }

            @Override
            public void onError(JSONRPC2Error error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(JSONObject error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(int userId) throws Exception {
                super.onError(userId);
                callback.loadingFailed();
            }

            @Override
            public void onErrorDismissed() {
                super.onErrorDismissed();
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void loadThreads(final Activity activity, final Category category, boolean lockWithLoadingScreen) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().getCategoryContent(category.getId(), type.toString(), lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                boolean newData = false;

                try {
                    List<Thread> resultThreads = (LinkedList<Thread>) (deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.THREADS)));

                    if (!category.getThreads().equals(resultThreads)) {
                        category.setThreads(resultThreads);
                        for(Thread thread: category.getThreads()){
                            thread.getAccount().setProfilePicture(MainActivity.cachedAccounts.get(thread.getAccount().getId()));
                        }
                        newData = true;
                    }

                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, "exception", e);
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();

                if (newData) {
                    Log.d(Constants.Log.TAG, "New threads in category: " + category.getHeadLine());
                    callback.threadsLoaded();
                }
            }

            @Override
            public void onError(JSONRPC2Error error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(JSONObject error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(int userId) throws Exception {
                super.onError(userId);
                callback.loadingFailed();
            }

            @Override
            public void onErrorDismissed() {
                super.onErrorDismissed();
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    public void loadNotes(final Activity activity, final PrivateCategory category, boolean lockWithLoadingScreen) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().getCategoryContent(category.getId(), type.toString(), lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                try {
                    List<Note> resultNotes = (LinkedList<Note>) (deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.NOTES)));
                    category.setNotes(resultNotes);
                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, "exception", e);
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();
                callback.notesLoaded();
            }

            @Override
            public void onError(JSONRPC2Error error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(JSONObject error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(int userId) throws Exception {
                super.onError(userId);
                callback.loadingFailed();
            }

            @Override
            public void onErrorDismissed() {
                super.onErrorDismissed();
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void loadLatestThreads(final Activity activity, int limit, boolean lockWithLoadingScreen) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().getLatestThreads(limit, lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                boolean newData = false;

                try {
                    List<Thread> resultThreads = (LinkedList<Thread>) (deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.THREADS)));

                    if (!latestThreads.equals(resultThreads)) {
                        latestThreads = resultThreads;
                        newData = true;
                    }

                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, "exception", e);
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();

                if (newData) {
                    Log.d(Constants.Log.TAG, "New latest threads");
                    callback.threadsLoaded();
                }
            }

            @Override
            public void onError(JSONRPC2Error error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(JSONObject error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(int userId) throws Exception {
                super.onError(userId);
                callback.loadingFailed();
            }

            @Override
            public void onErrorDismissed() {
                super.onErrorDismissed();
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void loadReplies(final Activity activity, final Thread thread, SortType sortType, boolean lockWithLoadingScreen) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().getThreadContent(thread.getId(), sortType.getValue(), lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                boolean newData = false;

                try {
                    List<Reply> resultReplies = (LinkedList<Reply>) (deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.REPLIES)));

                    if (!thread.getReplies().equals(resultReplies)) {
                        thread.setReplies(resultReplies);
                        for(Reply reply: thread.getReplies()){
                            reply.getAccount().setProfilePicture(MainActivity.cachedAccounts.get(thread.getAccount().getId()));
                        }
                        newData = true;
                    }
                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, e.toString());
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();

                if (newData) {
                    Log.d(Constants.Log.TAG, "New replies in thread: " + thread.getHeadLine());
                    callback.repliesLoaded();
                }
            }

            @Override
            public void onError(JSONRPC2Error error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(JSONObject error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(int userId) throws Exception {
                super.onError(userId);
                callback.loadingFailed();
            }

            @Override
            public void onErrorDismissed() {
                super.onErrorDismissed();
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void createThread(final Activity activity, int categoryID, String headline, String text, boolean lockWithLoadingScreen) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().createThread(categoryID, headline, text, lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);


                callback.threadCreated((Thread) (deSerialize(Thread.class, (String) result.get(Constants.Transfer.THREAD))));
            }

            @Override
            public void onError(JSONRPC2Error error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(JSONObject error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(int userId) throws Exception {
                super.onError(userId);
                callback.loadingFailed();
            }

            @Override
            public void onErrorDismissed() {
                super.onErrorDismissed();
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void createReply(final Activity activity, int threadID, String text, boolean lockWithLoadingScreen) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().createReply(threadID, text, lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);


                callback.replyCreated((Reply) (deSerialize(Reply.class, (String) result.get(Constants.Transfer.REPLY))));
            }

            @Override
            public void onError(JSONRPC2Error error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(JSONObject error, int userId) throws Exception {
                super.onError(error, userId);
                callback.loadingFailed();
            }

            @Override
            public void onError(int userId) throws Exception {
                super.onError(userId);
                callback.loadingFailed();
            }

            @Override
            public void onErrorDismissed() {
                super.onErrorDismissed();
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    public enum ForumType {
        PUBLIC(Constants.Transfer.PUBLIC),
        PRIVATE(Constants.Transfer.PRIVATE);

        private final String text;

        private ForumType(String text) {
            this.text = text;
        }


        @Override
        public String toString() {
            return text;
        }
    }
}
