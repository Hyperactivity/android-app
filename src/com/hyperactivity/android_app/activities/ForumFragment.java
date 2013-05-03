package com.hyperactivity.android_app.activities;

import android.graphics.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.Utils;
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
        getFragmentManager().beginTransaction().replace(R.id.forum_thread_list_container, threadList).commit();
        scrollPicker = (ScrollPicker) view.findViewById(R.id.forum_categories_surface_view);
        scrollPicker.getThread().setState(ScrollPicker.ScrollPickerThread.STATE_READY);
        scrollPicker.getThread().setCallback(this);

        return view;
    }

    public void updateThreadList() {
        updateThreadList(scrollPicker.getItemManager().getSelectedItem().getCategory().getThreads());
    }

    public void updateThreadList(List<Thread> threads) {
        threadList.updateThreadList(threads);
    }

    @Override
    public void selectedItemChanged(ScrollPickerItem selected) {
        if (selected != null) {
            ((Engine) getActivity().getApplication()).getPublicForum().loadThreads(getActivity(), selected.getCategory(), false);
        }

        //This callback will be executed as the scrollpicker thread. Change to UI because UI stuff is gonna be done.
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                updateThreadList(new LinkedList<Thread>());
            }
        });
    }

    @Override
    public void scrollPickerReady() {
        updateCategories();
    }

    public void updateCategories() {
        if (scrollPicker != null && scrollPicker.getThread().isReady()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            scrollPicker.reset();

            Iterator<Category> it = ((Engine) getActivity().getApplication()).getPublicForum().getCategories().iterator();

            while (it.hasNext()) {
                Category category = it.next();

                Bitmap image = null;

                String filename = category.getImageName();

                if (filename != null) {
                    image = Utils.getBitmapFromAsset(getActivity(), filename);
                }

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
    }

    public void test() {
        scrollPicker.getThread().pause();
    }
}
