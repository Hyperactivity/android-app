package com.hyperactivity.android_app.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.ForumThread;
import com.hyperactivity.android_app.forum.models.Category;

public class SearchFragment extends Fragment {
	
	ThreadListFragment searchResultList;
	TextView noResultsText;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_fragment, null);
		
		searchResultList = new ThreadListFragment();
		searchResultList.updateThreadList(new ArrayList<ForumThread>());
		getFragmentManager().beginTransaction().replace(R.id.search_thread_list_container, searchResultList).commit();
		
		final EditText editText = (EditText)view.findViewById(R.id.search_text_field);
		final Button button = (Button)view.findViewById(R.id.search_button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = editText.getText().toString().trim();
				querySearchString(text);
			}
		});
		
		return view;
	}
	
	private void querySearchString(String text) {
		ArrayList<ForumThread> result = new ArrayList<ForumThread>();
		
		for (Category category : ((Engine) getActivity().getApplication()).getPublicForum().getCategories()) {
			if (category.getThreads() == null) continue;
			for (com.hyperactivity.android_app.forum.models.Thread thread : category.getThreads()) {
				if (thread.getText().contains(text) || thread.getHeadLine().contains(text)) {
					result.add(new ForumThread(null, thread.getHeadLine(), thread.getText()));
				}
			}
		}
		
		searchResultList.updateThreadList(result);
	}
	
}
