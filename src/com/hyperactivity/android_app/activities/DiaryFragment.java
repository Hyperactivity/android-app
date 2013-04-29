package com.hyperactivity.android_app.activities;

import com.hyperactivity.android_app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiaryFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.diary_fragment, null);
	}
}
