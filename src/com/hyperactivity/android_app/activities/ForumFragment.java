package com.hyperactivity.android_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyperactivity.android_app.R;

public class ForumFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//		return null;
		return inflater.inflate(R.layout.forum_fragment, null);
	}
}
