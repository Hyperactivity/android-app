package com.hyperactivity.android_app.forum;

import android.app.Activity;
import android.util.Log;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.Category;
import com.hyperactivity.android_app.forum.models.Thread;
import com.hyperactivity.android_app.network.NetworkCallback;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import net.minidev.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

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

    @SuppressWarnings("unchecked")
    public void loadCategories(final Activity activity) {
        callback.loadingStarted();

        ((Engine) activity.getApplicationContext()).getServerLink().getForumContent(type.toString(), new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                try {
                    categories = (LinkedList<Category>) deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.CATEGORIES));
                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, e.toString());
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();
                callback.categoriesLoaded();
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

        ((Engine) activity.getApplicationContext()).getServerLink().getCategoryContent(category.getId(), lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                try {
                    //TODO: this should be rewritten when we get linkedlists from server.
                    category.setThreads(new LinkedList<Thread>(deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.THREADS))));
                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, e.toString());
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();
                callback.threadsLoaded();
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

                try {
                    latestThreads = new LinkedList<Thread>(deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.THREADS)));
                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, e.toString());
                    callback.loadingFailed();
                    return;
                }

                callback.loadingFinished();
                callback.threadsLoaded();
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
