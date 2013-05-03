package com.hyperactivity.android_app.activities;

import com.hyperactivity.android_app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateThreadFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.create_thread_fragment, null);
		
		return view;
	}
	
}
