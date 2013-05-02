package com.hyperactivity.android_app.activities;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.ScrollPicker;
import com.hyperactivity.android_app.core.ScrollPickerEventCallback;
import com.hyperactivity.android_app.core.ScrollPickerItem;
import com.hyperactivity.android_app.forum.ForumThread;
import com.hyperactivity.android_app.forum.models.Category;
import com.hyperactivity.android_app.forum.models.Thread;

public class ForumFragment extends Fragment implements ScrollPickerEventCallback {
	
    private ScrollPicker scrollPicker;
	private ThreadListFragment threadList;
	private boolean categoriesLoaded;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.forum_fragment, null);
		threadList = new ThreadListFragment();
        getFragmentManager().beginTransaction().replace(R.id.forum_thread_list_container, threadList).commit();
        scrollPicker = (ScrollPicker) view.findViewById(R.id.forum_categories_surface_view);
        scrollPicker.getThread().setState(ScrollPicker.ScrollPickerThread.STATE_READY);
        scrollPicker.getThread().getItemManager().setCallback(this);
        if (categoriesLoaded) {
        	updateCategories();
        }
        return view;
	}
	
	public void updateCategories() {
		// TODO callback when scrollpicker is properly created?
		if (scrollPicker != null) {
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inScaled = false;
	        options.inDither = false;
	        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

	        scrollPicker.reset();

	        Iterator<Category> it = ((Engine) getActivity().getApplication()).getPublicForum().getCategories().iterator();

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
	        categoriesLoaded = false;
		} else {
			categoriesLoaded = true;
		}
	}
	
	public void updateThreadList() {
        ArrayList<ForumThread> chosenThreads = new ArrayList<ForumThread>();
        Iterator<Thread> it = scrollPicker.getItemManager().getSelectedItem().getCategory().getThreads().iterator();

        while (it.hasNext()) {
            Thread thread = it.next();

            chosenThreads .add(new ForumThread(null, thread.getHeadLine(), thread.getText()));
        }
        updateThreadList(chosenThreads);
	}
	
	public void updateThreadList(ArrayList<ForumThread> forumList) {
        threadList.updateThreadList(forumList);
	}

    @Override
    public void selectedItemChanged(ScrollPickerItem selected) {
        updateThreadList( new ArrayList<ForumThread>());

        if(selected != null) {
            ((Engine) getActivity().getApplication()).getPublicForum().loadThreads(getActivity(), selected.getCategory(), false);
        }
    }
}
