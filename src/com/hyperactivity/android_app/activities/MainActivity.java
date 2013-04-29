package com.hyperactivity.android_app.activities;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.ScrollPicker;
import com.hyperactivity.android_app.forum.ForumThread;

public class MainActivity extends FragmentActivity {
	
	public static final int HOME_FRAGMENT = 0,
							FORUM_FRAGMENT = 1,
							DIARY_FRAGMENT = 2;

    private Boolean isLocked;
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
        scrollPicker.getThread().setState(
                ScrollPicker.ScrollPickerThread.STATE_READY);

        // Make a link from the navigation menu to this activity
        NavigationMenuFragment navigationMenu = (NavigationMenuFragment) getSupportFragmentManager()
        .findFragmentById(R.id.navigation_menu_fragment);
        navigationMenu.setParentActivity(this);
    }
    
    private void initializeFragments() {
    	fragments = new Fragment[3];
    	fragments[HOME_FRAGMENT] = new ThreadListFragment();
    	fragments[FORUM_FRAGMENT] = new ForumFragment();
    	fragments[DIARY_FRAGMENT] = new DiaryFragment();
    }
    
    public void changeFragment(int fragmentID) {
    	if (currentFragment != fragmentID) {
    		currentFragment = fragmentID;
        	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        	transaction.replace(R.id.main_fragment_container, fragments[fragmentID]);
        	transaction.commit();
        	
//        	if (currentFragment == HOME_FRAGMENT) { // Update thread list
//                ArrayList<ForumThread> forumList = new ArrayList<ForumThread>();
//                forumList.add(new ForumThread(null, "test1", "test12"));
//                forumList.add(new ForumThread(null, "test2", "test22"));
//                forumList.add(new ForumThread(null, "test3", "test32"));
//                ((ThreadListFragment)fragments[HOME_FRAGMENT]).updateThreadList(forumList);
//        	}
    	}
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
}
