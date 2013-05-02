package com.hyperactivity.android_app.activities;

import java.util.ArrayList;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.forum.ForumThread;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
	
	ThreadListFragment threadList;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment, null);
		threadList = new ThreadListFragment();
        getFragmentManager().beginTransaction().replace(R.id.latest_thread_list_container, threadList).commit();
		return view;
	}
	
	public void updateThreadList(ArrayList<ForumThread> forumList) {
		for (ForumThread thread : forumList) {
			System.out.println("Thread: " + thread.getHeadline());
		}
        threadList.updateThreadList(forumList);
	}
}
