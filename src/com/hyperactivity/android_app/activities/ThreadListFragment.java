package com.hyperactivity.android_app.activities;

import java.util.*;

import com.hyperactivity.android_app.R;

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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_2, threads);
 
		String[] from = new String[] {"thread_headline", "thread_text"};
		int[] to = new int[] {R.id.thread_headline, R.id.thread_text};
		
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> row1 = new HashMap<String, String>();
		row1.put("thread_headline", "Test row 1");
		row1.put("thread_text", "Hej! jag tänkte att liksom så atteehh...");
		data.add(row1);
		
		HashMap<String, String> row2 = new HashMap<String, String>();
		row2.put("thread_headline", "hejahksdjfhkj");
		row2.put("thread_text", "Hej!fsakjl oaskopsdk fok");
		data.add(row2);
		
        SimpleAdapter adapter = new SimpleAdapter(inflater.getContext(), data, R.layout.thread_list_item, from, to);
        
        /** Setting the list adapter for the ListFragment */
        setListAdapter(adapter);
 
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
