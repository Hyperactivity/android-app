package com.hyperactivity.android_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.hyperactivity.android_app.forum.models.Thread;

import java.util.List;
import android.widget.TextView;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.AdminActionCallback;
import com.hyperactivity.android_app.core.Engine;

public class HomeFragment extends Fragment {

    ThreadListFragment threadList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        threadList = new ThreadListFragment();
        threadList.setShowCategoryImages();

        Button searchButton = (Button) view.findViewById(R.id.go_to_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).changeFragment(MainActivity.SEARCH_FRAGMENT);
            }
        });

        TextView caption = (TextView) view.findViewById(R.id.caption).findViewById(R.id.caption_text);
        caption.setText((String) getResources().getText(R.string.latest_threads));

        if (getActivity() instanceof AdminActionCallback) {
            threadList.setCallback((AdminActionCallback) getActivity());
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction().replace(R.id.latest_thread_list_container, threadList).commit();
        updateThreadList();
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().beginTransaction().remove(threadList).commit();
    }

    public List<Thread> updateThreadList() {
        List<Thread> threads = ((Engine)getActivity().getApplication()).getPublicForum().getLatestThreads(getActivity(), 10);
        threadList.updateThreadList(threads);
        return threads;
	}
}
