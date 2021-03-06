package com.hyperactivity.android_app.activities;

import android.graphics.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.ScrollPicker;
import com.hyperactivity.android_app.core.ScrollPickerEventCallback;
import com.hyperactivity.android_app.core.ScrollPickerItem;
import com.hyperactivity.android_app.forum.models.Category;
import com.hyperactivity.android_app.forum.models.Thread;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ForumFragment extends Fragment implements ScrollPickerEventCallback {

    private ScrollPicker scrollPicker;
    private ThreadListFragment threadList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, null);
        threadList = new ThreadListFragment();
        threadList.setShowProfileImages();

        scrollPicker = (ScrollPicker) view.findViewById(R.id.forum_categories_surface_view);
        scrollPicker.setZOrderOnTop(true);
        scrollPicker.getThread().setState(ScrollPicker.ScrollPickerThread.STATE_READY);
        scrollPicker.getThread().setCallback(this);

        Engine engine = ((Engine) getActivity().getApplication());
        scrollPicker.getItemManager().addCategories(getActivity(), engine.getPublicForum().getCategories(getActivity()), Color.BLACK);

        TextView caption = (TextView)view.findViewById(R.id.caption).findViewById(R.id.caption_text);
        caption.setText((String)getResources().getText(R.string.forum));

        return view;
    }

    public List<Thread>  updateThreadList() {
        List<Thread> threads = scrollPicker.getItemManager().getSelectedItem().getCategory().getThreads();
        updateThreadList(threads);
        return threads;
    }

    public void updateThreadList(List<Thread> threads) {
        System.out.println("Updating thread list!");
        threadList.updateThreadList(threads);
    }

    @Override
    public void selectedItemChanged(final ScrollPickerItem selected) {
        //This callback will be executed as the scrollpicker thread.

        final List<Thread> threads;
        if (selected != null) {
            threads = ((Engine) getActivity().getApplication()).getPublicForum().getThreads(getActivity(), selected.getCategory());
        } else {
            threads = new LinkedList<Thread>();
        }

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                updateThreadList(threads);
            }
        });
    }

    @Override
    public void scrollPickerReady() {
        updateCategories();
    }

    public void updateCategories() {
        if (scrollPicker != null && scrollPicker.getThread().isReady()) {
            scrollPicker.reset();

            Iterator<Category> it = ((Engine) getActivity().getApplication()).getPublicForum().getCategories().iterator();

            while (it.hasNext()) {
                Category category = it.next();

                scrollPicker.getItemManager().addItem(category.getImage(getActivity()), category.getHeadLine(), Color.BLACK, category);
            }

            scrollPicker.getItemManager().recalculateItems();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().beginTransaction().remove(threadList).commit();
        scrollPicker.getThread().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction().replace(R.id.forum_thread_list_container, threadList).commit();
        scrollPicker.getThread().unpause();
    }
}
