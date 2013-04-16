package com.hyperactivity.android_app.activities;

import java.util.*;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.forum.ForumThread;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

@TargetApi(16)
public class ThreadListFragment extends ListFragment {

	public void updateThreadList(List<ForumThread> threadList) {
		String[] from = new String[] {"thread_headline", "thread_text"};
		int[] to = new int[] {R.id.thread_headline, R.id.thread_text};
		
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
				
		for(int i = 0; i < threadList.size(); i++) {
			ForumThread thread = threadList.get(i);
			data.add(threadToMap(thread));
		}
		
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.thread_list_item, from, to);
        
        /** Setting the list adapter for the ListFragment */
        setListAdapter(adapter);
 
    }
	
	private HashMap<String, String> threadToMap(ForumThread thread) {
		String headline = thread.getHeadline();
		String text = thread.getText();
		HashMap<String, String> row = new HashMap<String, String>();
		row.put("thread_headline", headline);
		row.put("thread_text", text);
		return row;
	}
}