package com.hyperactivity.android_app.activities;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.forum.models.Thread;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewThreadFragment extends Fragment {
	
	private Thread currentThread;
	private TextView headlineField, textField;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.view_thread_fragment, null);
		
		headlineField = (TextView)view.findViewById(R.id.thread_headline_field);
		textField = (TextView)view.findViewById(R.id.thread_text_field);
		
		return view;
	}
	
	public void onResume() {
		super.onResume();
		if (currentThread != null) {
			headlineField.setText(currentThread.getHeadLine());
			textField.setText(currentThread.getText());
		}
	}
	
	public void setCurrentThread(Thread thread) {
		currentThread = thread;
	}
	
}
