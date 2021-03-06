package com.hyperactivity.android_app.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.Category;
import com.hyperactivity.android_app.forum.models.Thread;

public class SearchFragment extends Fragment {
	
	ThreadListFragment searchResultList;
	TextView noResultsText;
    EditText searchEditText;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_fragment, null);
		
		searchResultList = new ThreadListFragment();
		searchResultList.updateThreadList(new ArrayList<Thread>());

        searchEditText = (EditText)view.findViewById(R.id.search_text_field);

		final EditText editText = searchEditText;
		final Button button = (Button)view.findViewById(R.id.search_button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = editText.getText().toString().trim();
				querySearchString(text);
			}
		});

        TextView caption = (TextView)view.findViewById(R.id.caption).findViewById(R.id.caption_text);
        caption.setText((String)getResources().getText(R.string.search));
		
		return view;
	}
	
	private void querySearchString(String text) {
		ArrayList<Thread> result = new ArrayList<Thread>();

        HashSet<Thread> resultSet = new HashSet<Thread>();
		for (Category category : ((Engine) getActivity().getApplication()).getPublicForum().getCategories()) {
			if (category.getThreads() == null) continue;
			for (Thread thread : category.getThreads()) {
				if (threadMatches(thread, text)) {
					resultSet.add(thread);
				}
			}
		}
        for (Thread thread : ((Engine) getActivity().getApplication()).getPublicForum().getLatestThreads()) {
            if (threadMatches(thread, text)) {
                resultSet.add(thread);
            }
        }
        for (Thread thread : resultSet) result.add(thread);
		
		searchResultList.updateThreadList(result);
	}

    private boolean threadMatches(Thread thread, String query) {
        String text = query.toLowerCase(Locale.ENGLISH);
        return thread.getText().toLowerCase(Locale.ENGLISH).contains(text) || thread.getHeadLine().toLowerCase(Locale.ENGLISH).contains(text);
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().beginTransaction().remove(searchResultList).commit();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction().replace(R.id.search_thread_list_container, searchResultList).commit();
    }
}
