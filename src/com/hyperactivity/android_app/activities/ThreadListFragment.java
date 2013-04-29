package com.hyperactivity.android_app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.forum.ForumThread;

public class ThreadListFragment extends ListFragment {
	
	private String[] from = new String[] {"thread_headline", "thread_text"};
	private int[] to = new int[] {R.id.thread_headline, R.id.thread_text};
	
	private List<HashMap<String, String>> data;

	public void updateThreadList(List<ForumThread> threadList) {
		data = new ArrayList<HashMap<String, String>>();
				
		for(int i = 0; i < threadList.size(); i++) {
			ForumThread thread = threadList.get(i);
			data.add(threadToMap(thread));
		}
		
		if (getActivity() != null) {
	        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.thread_list_item, from, to);
	        setListAdapter(adapter);
	        data = null;
		}
    }
	
	@Override
	public void onResume() {
		super.onResume();
		if (data != null) {
			SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.thread_list_item, from, to);
	        setListAdapter(adapter);
	        data = null;
		}
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