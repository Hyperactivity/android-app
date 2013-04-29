package com.hyperactivity.android_app.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.*;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.ScrollPicker;
import com.hyperactivity.android_app.forum.ForumEventCallback;
import com.hyperactivity.android_app.forum.ForumThread;
import com.hyperactivity.android_app.network.NetworkCallback;
import com.hyperactivity.android_app.forum.models.Category;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends FragmentActivity implements ForumEventCallback {
    private ScrollPicker scrollPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.forum_categories_surface_view);
        scrollPicker = (ScrollPicker) view;
        scrollPicker.getThread().setState(ScrollPicker.ScrollPickerThread.STATE_READY);

        Engine engine = (Engine) getApplication();
        engine.getPublicForum().setCallback(this);
        engine.getPublicForum().loadCategories(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Triggers when an item in the menu is clicked such as "Settings" or "Help"
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // Settings has been clicked, check the android version to decide if
                // to use fragmented settings or not
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    startActivity(new Intent(this, SettingsActivity.class));
                } else {
                    startActivity(new Intent(this, SettingsActivityHoneycomb.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadingFinished() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        scrollPicker.reset();

        Iterator<Category> it = ((Engine) getApplication()).getPublicForum().getCategories().iterator();

        while (it.hasNext()) {
            Category category = it.next();

            //TODO: try do load the image somehow.
            Bitmap image = null;

            if (image == null) {
                image = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(image);
                Paint paint = new Paint();
                paint.setColor(-category.getColorCode());
                c.drawCircle(300 / 2, 300 / 2, 300 / 2, paint);
            }

            scrollPicker.getItemManager().addItem(image, category.getHeadLine(), Color.BLACK, category);
        }

        scrollPicker.getItemManager().recalculateItems();

        //TODO: this is dummy data.
        ArrayList<ForumThread> forumList = new ArrayList<ForumThread>();
        forumList.add(new ForumThread(null, "test1", "test12"));
        forumList.add(new ForumThread(null, "test2", "test22"));
        forumList.add(new ForumThread(null, "test3", "test32"));

        ThreadListFragment threadListFragment = (ThreadListFragment) getSupportFragmentManager().findFragmentById(R.id.thread_list);

        threadListFragment.updateThreadList(forumList);
    }

    @Override
    public void loadingStarted() {
    }

    @Override
    public void loadingFailed() {
    }
}
