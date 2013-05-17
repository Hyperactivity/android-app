package com.hyperactivity.android_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.ForumEventCallback;
import com.hyperactivity.android_app.forum.SortType;
import com.hyperactivity.android_app.forum.models.Reply;
import com.hyperactivity.android_app.forum.models.Thread;

public class MainActivity extends FragmentActivity implements ForumEventCallback {
    public static final int HOME_FRAGMENT = 0,
            FORUM_FRAGMENT = 1,
            DIARY_FRAGMENT = 2,
            CREATE_THREAD_FRAGMENT = 3,
            SEARCH_FRAGMENT = 4,
            VIEW_THREAD_FRAGMENT = 5,
            SETTINGS_FRAGMENT = 6,
            CHAT_FRAGMENT = 7;

    private final String CURRENT_FRAGMENT = "current_fragment";

    private Fragment[] fragments;
    private int currentFragment;
    private NavigationMenuFragment navigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Make a link from the navigation menu to this activity
        navigationMenu = (NavigationMenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_menu_fragment);
        navigationMenu.setParentActivity(this);

        initializeFragments();

        if (savedInstanceState != null) {
            retainSavedState(savedInstanceState);
            int tmp = currentFragment;
            currentFragment = -1;
            changeFragment(tmp);
        } else {
            currentFragment = -1;
            changeFragment(HOME_FRAGMENT);
        }

        Engine engine = (Engine) getApplication();
        engine.getPublicForum().setCallback(this);
        engine.getPublicForum().loadCategories(this, false);

        engine.getPrivateForum().setCallback(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT, currentFragment);
    }

    private void retainSavedState(Bundle state) {
        currentFragment = state.getInt(CURRENT_FRAGMENT);
    }

    private void initializeFragments() {
        fragments = new Fragment[8];
        fragments[HOME_FRAGMENT] = new HomeFragment();
        fragments[FORUM_FRAGMENT] = new ForumFragment();
        fragments[DIARY_FRAGMENT] = new DiaryFragment();
        fragments[CREATE_THREAD_FRAGMENT] = new CreateThreadFragment();
        fragments[SEARCH_FRAGMENT] = new SearchFragment();
        fragments[VIEW_THREAD_FRAGMENT] = new ViewThreadFragment();
        fragments[SETTINGS_FRAGMENT] = new SettingsFragment();
        fragments[CHAT_FRAGMENT] = new ChatFragment();
    }

    public void visitThread(Thread thread) {
        ((Engine)getApplication()).getPublicForum().loadReplies(this, thread, SortType.STANDARD, false);
    	((ViewThreadFragment)fragments[VIEW_THREAD_FRAGMENT]).setCurrentThread(thread);
    	changeFragment(VIEW_THREAD_FRAGMENT);
    }

    public void changeFragment(int fragmentID) {
        if (currentFragment != fragmentID) {
            currentFragment = fragmentID;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment_container, fragments[fragmentID]);
            transaction.commit();
            navigationMenu.updateNavigationMenu(currentFragment);
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
        if(currentFragment == FORUM_FRAGMENT) {
            ((ForumFragment) fragments[FORUM_FRAGMENT]).updateCategories();
        } else if(currentFragment == DIARY_FRAGMENT) {
            ((DiaryFragment) fragments[DIARY_FRAGMENT]).updateCategories();
        } else if (currentFragment == CREATE_THREAD_FRAGMENT) {
            ((CreateThreadFragment) fragments[CREATE_THREAD_FRAGMENT]).updateCategories();
        }
    }

    @Override
    public void threadsLoaded() {
        if (currentFragment == FORUM_FRAGMENT) {
            ((ForumFragment) fragments[FORUM_FRAGMENT]).updateThreadList();
        } else if (currentFragment == DIARY_FRAGMENT) {
            ((DiaryFragment) fragments[DIARY_FRAGMENT]).updateThreadList();
        } else if (currentFragment == HOME_FRAGMENT) {
            // Get latest threads
            ((HomeFragment) fragments[HOME_FRAGMENT]).updateThreadList();
        }
    }

    @Override
    public void repliesLoaded() {
    	if (currentFragment == VIEW_THREAD_FRAGMENT) {
    		((ViewThreadFragment)fragments[VIEW_THREAD_FRAGMENT]).updateReplies();
    	}
    }

    @Override
    public void threadCreated(Thread thread) {
        if (currentFragment == CREATE_THREAD_FRAGMENT) {
            visitThread(thread);
        }
    }

	@Override
	public void replyCreated(Reply reply) {
        visitThread(reply.getParentThread());
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public void onSettingsClick(MenuItem item) {
        changeFragment(SETTINGS_FRAGMENT);
    }
}
