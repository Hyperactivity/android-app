package com.hyperactivity.android_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.ForumEventCallback;

public class MainActivity extends FragmentActivity implements ForumEventCallback {
    public static final int HOME_FRAGMENT = 0,
            FORUM_FRAGMENT = 1,
            DIARY_FRAGMENT = 2;

    private Fragment[] fragments;
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFragments();
        currentFragment = -1;
        changeFragment(HOME_FRAGMENT);

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
		((ForumFragment)fragments[FORUM_FRAGMENT]).updateCategories();
    }

    @Override
    public void threadsLoaded() {
    	if (currentFragment == FORUM_FRAGMENT) {
            ((ForumFragment)fragments[FORUM_FRAGMENT]).updateThreadList();
    	} else if (currentFragment == HOME_FRAGMENT) {
    		// Get latest threads
            ((HomeFragment)fragments[HOME_FRAGMENT]).updateThreadList();
    	}
    }

    @Override
    public void repliesLoaded() {
    }
}
