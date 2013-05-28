package com.hyperactivity.android_app.activities;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.AdminActionCallback;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.ForumEventCallback;
import com.hyperactivity.android_app.forum.SortType;
import com.hyperactivity.android_app.forum.models.*;
import com.hyperactivity.android_app.forum.models.Thread;
import com.hyperactivity.android_app.network.NetworkCallback;
import net.minidev.json.JSONObject;

import java.util.*;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements ForumEventCallback, AdminActionCallback {
    Activity mainActivity = this;
    static public HashMap<Integer,Bitmap> cachedAccounts = new HashMap<Integer, Bitmap>();


    public static final int HOME_FRAGMENT = 0,
            FORUM_FRAGMENT = 1,
            DIARY_FRAGMENT = 2,
            CREATE_THREAD_FRAGMENT = 3,
            SEARCH_FRAGMENT = 4,
            VIEW_THREAD_FRAGMENT = 5,
            SETTINGS_FRAGMENT = 6,
            VIEW_PROFILE_FRAGMENT = 7,
            CHAT_FRAGMENT = 8,
            VIEW_NOTE_FRAGMENT = 9,
            CREATE_NOTE_FRAGMENT = 10;

    private final String CURRENT_FRAGMENT = "current_fragment";
    private final String IN_PRIVATE_VIEW = "in_private_view";

    private Fragment[] fragments;
    private int currentFragment;
    private NavigationMenuFragment navigationMenu;
    private boolean inPrivateView;

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
            if (inPrivateView) {
                makeBlackBackground();
            }
            int tmp = currentFragment;
            currentFragment = -1;
            changeFragment(tmp);
        } else {
            currentFragment = -1;
            inPrivateView = false;
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
        outState.putBoolean(IN_PRIVATE_VIEW, inPrivateView);
    }

    private void retainSavedState(Bundle state) {
        currentFragment = state.getInt(CURRENT_FRAGMENT);
        inPrivateView = state.getBoolean(IN_PRIVATE_VIEW);
    }

    private void initializeFragments() {
        fragments = new Fragment[11];
        fragments[HOME_FRAGMENT] = new HomeFragment();
        fragments[FORUM_FRAGMENT] = new ForumFragment();
        fragments[DIARY_FRAGMENT] = new DiaryFragment();
        fragments[CREATE_THREAD_FRAGMENT] = new CreateThreadFragment();
        fragments[SEARCH_FRAGMENT] = new SearchFragment();
        fragments[VIEW_THREAD_FRAGMENT] = new ViewThreadFragment();
        fragments[SETTINGS_FRAGMENT] = new SettingsFragment();
        fragments[VIEW_PROFILE_FRAGMENT] = new ProfileFragment();
        fragments[CHAT_FRAGMENT] = new ChatFragment();
        fragments[VIEW_NOTE_FRAGMENT] = new ViewNoteFragment();
        fragments[CREATE_NOTE_FRAGMENT] = new CreateNoteFragment();
    }

    public void visitThread(Thread thread) {
        ((Engine) getApplication()).getPublicForum().loadReplies(this, thread, SortType.STANDARD, false);
        ((ViewThreadFragment) fragments[VIEW_THREAD_FRAGMENT]).setCurrentThread(thread);
        changeFragment(VIEW_THREAD_FRAGMENT);
    }

//    public void visitCategory(Category category) {
//        ((Engine) getApplication()).getPublicForum().loadThreads(this, category, false);
//        ((ViewThreadFragment) fragments[VIEW_THREAD_FRAGMENT]).set(thread);
//        changeFragment(VIEW_THREAD_FRAGMENT);
//    }

    public void visitNote(Note note) {
        ((ViewNoteFragment) fragments[VIEW_NOTE_FRAGMENT]).setCurrentNote(note);
        changeFragment(VIEW_NOTE_FRAGMENT);
    }

    public void visitAccount(Account account) {
        ((ProfileFragment) fragments[VIEW_PROFILE_FRAGMENT]).setCurrentAccount(account);
        changeFragment(VIEW_PROFILE_FRAGMENT);
    }

    public void changeFragment(int fragmentID) {
        if (currentFragment != fragmentID) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment_container, fragments[fragmentID]).addToBackStack(currentFragment+"");
            transaction.commit();

            currentFragment = fragmentID;
            updateBackground();
            navigationMenu.updateNavigationMenu(currentFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if(fragments[HOME_FRAGMENT].isVisible()){
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            try {
                currentFragment = Integer.parseInt(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName());
                navigationMenu.updateNavigationMenu(currentFragment);
                updateBackground();
            } catch (NumberFormatException e) {
                Log.d(Constants.Log.TAG, "Backstack entry didn't have number tag");
            }
        }
        Log.d(Constants.Log.TAG, "Pressed back button!");
        super.onBackPressed();
    }

    /**
     * Methods used for the background change in the diary. Ugly
     */
    public void updateBackground() {
        if (isPrivateFragment(currentFragment) && !inPrivateView) {
            makeBlackBackground();
        } else if (!isPrivateFragment(currentFragment) && inPrivateView) {
            restoreBackground();
        }
    }

    private boolean isPrivateFragment(int fragmentID) {
        return fragmentID == DIARY_FRAGMENT || fragmentID == VIEW_NOTE_FRAGMENT || fragmentID == CREATE_NOTE_FRAGMENT;
    }

    public void makeBlackBackground() {
        LinearLayout outerBackground = (LinearLayout) findViewById(R.id.outer_main_background);
        LinearLayout innerBackground = (LinearLayout) findViewById(R.id.inner_main_background);

        int[] outerPadding = getPaddingFromLinearLayout(outerBackground);
        int[] innerPadding = getPaddingFromLinearLayout(innerBackground);

        outerBackground.setBackgroundResource(R.color.black);
        innerBackground.setBackgroundResource(R.color.black);

        outerBackground.setPadding(outerPadding[0], outerPadding[1], outerPadding[2], outerPadding[3]);
        innerBackground.setPadding(innerPadding[0], innerPadding[1], innerPadding[2], innerPadding[3]);
        inPrivateView = true;
    }

    public void restoreBackground() {
        LinearLayout outerBackground = (LinearLayout) findViewById(R.id.outer_main_background);
        LinearLayout innerBackground = (LinearLayout) findViewById(R.id.inner_main_background);

        int[] outerPadding = getPaddingFromLinearLayout(outerBackground);
        int[] innerPadding = getPaddingFromLinearLayout(innerBackground);

        outerBackground.setBackgroundResource(R.color.gray);
        innerBackground.setBackgroundResource(R.drawable.main_background);

        outerBackground.setPadding(outerPadding[0], outerPadding[1], outerPadding[2], outerPadding[3]);
        innerBackground.setPadding(innerPadding[0], innerPadding[1], innerPadding[2], innerPadding[3]);
        inPrivateView = false;
    }

    private int[] getPaddingFromLinearLayout(LinearLayout ll) {
        int[] paddingList = new int[4];
        paddingList[0] = ll.getPaddingLeft();
        paddingList[1] = ll.getPaddingTop();
        paddingList[2] = ll.getPaddingRight();
        paddingList[3] = ll.getPaddingBottom();
        return paddingList;
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
        if (currentFragment == FORUM_FRAGMENT) {
            ((ForumFragment) fragments[FORUM_FRAGMENT]).updateCategories();
        } else if (currentFragment == DIARY_FRAGMENT) {
            ((DiaryFragment) fragments[DIARY_FRAGMENT]).updateCategories();
        } else if (currentFragment == CREATE_THREAD_FRAGMENT) {
            ((CreateThreadFragment) fragments[CREATE_THREAD_FRAGMENT]).updateCategories();
        } else if (currentFragment == CREATE_NOTE_FRAGMENT) {
            ((CreateNoteFragment) fragments[currentFragment]).updateCategories();
        }

    }

    @Override
    public void threadsLoaded() {
        List<Thread> threads = new LinkedList<Thread>();
        if (currentFragment == FORUM_FRAGMENT) {
            threads = ((ForumFragment) fragments[FORUM_FRAGMENT]).updateThreadList();
        } else if (currentFragment == HOME_FRAGMENT) {
            // Get latest threads
            threads = ((HomeFragment) fragments[HOME_FRAGMENT]).updateThreadList();
        }
        Set<Account> profilePicUpdateList = new HashSet<Account>();
        for(Thread thread: threads){
            if(thread.getAccount().getProfilePicture() == null){
                profilePicUpdateList.add(thread.getAccount());
            }
        }
        if(!profilePicUpdateList.isEmpty()){
            ((Engine) this.getApplicationContext()).getServerLink().loadAvatars(cachedAccounts, Thread.class, profilePicUpdateList, this);
        }

    }

    @Override
    public void repliesLoaded() {
        List<Reply> replies = new LinkedList<Reply>();
        if (currentFragment == VIEW_THREAD_FRAGMENT) {
            replies =((ViewThreadFragment) fragments[VIEW_THREAD_FRAGMENT]).updateReplies();
        }
        Set<Account> profilePicUpdateList = new HashSet<Account>();
        for(Reply reply: replies){
            if(reply.getAccount().getProfilePicture() == null){
                profilePicUpdateList.add(reply.getAccount());
            }
        }
        if(!profilePicUpdateList.isEmpty()){
            ((Engine) this.getApplicationContext()).getServerLink().loadAvatars(cachedAccounts,Reply.class, profilePicUpdateList, this);
        }
    }

    @Override
    public void notesLoaded() {
        if (currentFragment == DIARY_FRAGMENT) {
            ((DiaryFragment) fragments[currentFragment]).updateNoteList();
        }
    }

    @Override
    public void threadCreated(Thread thread) {
        if (currentFragment == CREATE_THREAD_FRAGMENT) {
            visitThread(thread);
        }
    }

    @Override
    public void noteCreated(Note note) {
        if (currentFragment == CREATE_NOTE_FRAGMENT) {
            visitNote(note);
        }
    }

    @Override
    public void replyCreated(Reply reply) {
        visitThread(reply.getParentThread());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                changeFragment(SETTINGS_FRAGMENT);
                return true;
        }

        return false;
    }

    //--------------- admin actions ------------
    @Override
    public void editThread(Thread thread) {
        //((Engine) this.getApplicationContext()).getServerLink().modifyThread();

    }

    @Override
    public void deleteThread(final Thread thread) {
        ((Engine) this.getApplicationContext()).getServerLink().deleteThread(thread.getId(), true, new NetworkCallback() {
            @Override
            public void onOther(String status, JSONObject result, int userId) throws Exception {
                super.onOther(status, result, userId);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);    //To change body of overridden methods use File | Settings | File Templates.
                for(Category c: ((Engine)(getApplication())).getPublicForum().getCategories()){
                    if(c.equals(thread.getParentCategory())){
                        c.getThreads().remove(thread);
                    }
                }
                    threadsLoaded();
//                ((Engine)(getApplication())).getPublicForum().loadThreads(getActivity(),thread.getParentCategory(), false);
//                categoriesLoaded();
            }

            @Override
            public Activity getActivity() {
                return mainActivity;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Override
    public void editReply(Reply reply) {
    }

    @Override
    public void deleteReply(final Reply reply) {
        ((Engine) this.getApplicationContext()).getServerLink().deleteReply(reply.getId(), true, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);    //To change body of overridden methods use File | Settings | File Templates.
//                ((Engine)(getApplication())).getPublicForum().loadReplies(getActivity(), reply.getParentThread(), SortType.STANDARD, false);
//                ((ViewThreadFragment) fragments[VIEW_THREAD_FRAGMENT]).setCurrentThread(reply.getParentThread());
                for(Category c: ((Engine)(getApplication())).getPublicForum().getCategories()){
                    if(c.equals(reply.getParentThread().getParentCategory())){
                        for(Thread t: c.getThreads()){
                            if(t.equals(reply.getParentThread())){
                                t.getReplies().remove(reply);
                            }
                        }
                    }
                }
                repliesLoaded();

            }

            @Override
            public void onOther(String status, JSONObject result, int userId) throws Exception {
                super.onOther(status, result, userId);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public Activity getActivity() {
                return mainActivity;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    public void banAccount(Account account) {

    }
}


