package com.hyperactivity.android_app.activities;

import android.graphics.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.ScrollPicker;
import com.hyperactivity.android_app.core.ScrollPickerEventCallback;
import com.hyperactivity.android_app.core.ScrollPickerItem;
import com.hyperactivity.android_app.forum.ForumEventCallback;
import com.hyperactivity.android_app.forum.ForumThread;
import com.hyperactivity.android_app.forum.models.Category;
import com.hyperactivity.android_app.forum.models.Thread;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends FragmentActivity implements ForumEventCallback, ScrollPickerEventCallback {
    public static final int HOME_FRAGMENT = 0,
            FORUM_FRAGMENT = 1,
            DIARY_FRAGMENT = 2;
    private ScrollPicker scrollPicker;
    private Fragment[] fragments;
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFragments();
        currentFragment = -1;
        changeFragment(HOME_FRAGMENT);

        View view = findViewById(R.id.forum_categories_surface_view);
        scrollPicker = (ScrollPicker) view;
        scrollPicker.getThread().setState(ScrollPicker.ScrollPickerThread.STATE_READY);
        scrollPicker.getThread().getItemManager().setCallback(this);

        // Make a link from the navigation menu to this activity
        NavigationMenuFragment navigationMenu = (NavigationMenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_menu_fragment);
        navigationMenu.setParentActivity(this);

        Engine engine = (Engine) getApplication();
        engine.getPublicForum().setCallback(this);
        engine.getPublicForum().loadCategories(this);
    }

    private void initializeFragments() {
        fragments = new Fragment[3];
        fragments[HOME_FRAGMENT] = new HomeFragment();
        fragments[FORUM_FRAGMENT] = new ForumFragment();
        fragments[DIARY_FRAGMENT] = new DiaryFragment();
    }

    public void changeFragment(int fragmentID) {
        if (currentFragment != fragmentID) {
            currentFragment = fragmentID;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment_container, fragments[fragmentID]);
            transaction.commit();
        }
    }

    @Override
    public void loadingStarted() {
    }

    @Override
    public void loadingFailed() {
    }

    /**
     * Called when forum have finished loading.
     */
    @Override
    public void loadingFinished() {
    }

    @Override
    public void categoriesLoaded() {
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
    }

    @Override
    public void threadsLoaded() {
        ArrayList<ForumThread> forumList = new ArrayList<ForumThread>();
        Iterator<Thread> it = scrollPicker.getItemManager().getSelectedItem().getCategory().getThreads().iterator();

        while (it.hasNext()) {
            Thread thread = it.next();

            forumList.add(new ForumThread(null, thread.getHeadLine(), thread.getText()));
        }

        ThreadListFragment threadListFragment = (ThreadListFragment) fragments[HOME_FRAGMENT];
        threadListFragment.updateThreadList(forumList);
    }

    @Override
    public void repliesLoaded() {
    }


    @Override
    public void selectedItemChanged(ScrollPickerItem selected) {
        if(selected != null) {
            ((Engine) getApplication()).getPublicForum().loadThreads(this, selected.getCategory());
        }
    }
}
