package com.hyperactivity.android_app.activities;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyperactivity.android_app.R;

public class NavigationMenuFragment extends Fragment {
	
	public static final int NUMBER_OF_BUTTONS = 5;
	

	private static NavigationButtonInfo[] buttonInfo;
	private static HashMap<Integer, Integer> activeButtonFragmentMapping;
	
	static {
		buttonInfo = new NavigationButtonInfo[NUMBER_OF_BUTTONS];
		buttonInfo[0] = new NavigationButtonInfo(R.id.navigation_menu_button_1, R.drawable.active_house, R.drawable.non_active_house, MainActivity.HOME_FRAGMENT);
		buttonInfo[1] = new NavigationButtonInfo(R.id.navigation_menu_button_2, R.drawable.active_bubble, R.drawable.non_active_bubble, MainActivity.FORUM_FRAGMENT);
		buttonInfo[2] = new NavigationButtonInfo(R.id.navigation_menu_button_3, R.drawable.active_diary, R.drawable.non_active_diary, MainActivity.DIARY_FRAGMENT);
		buttonInfo[3] = new NavigationButtonInfo(R.id.navigation_menu_button_4, R.drawable.active_pen, R.drawable.non_active_pen, MainActivity.CREATE_THREAD_FRAGMENT);
		buttonInfo[4] = new NavigationButtonInfo(R.id.navigation_menu_button_5, R.drawable.active_search, R.drawable.non_active_search, MainActivity.SEARCH_FRAGMENT);
		
		activeButtonFragmentMapping = new HashMap<Integer, Integer>();
		activeButtonFragmentMapping.put(MainActivity.HOME_FRAGMENT, 0);
		activeButtonFragmentMapping.put(MainActivity.FORUM_FRAGMENT, 1);
		activeButtonFragmentMapping.put(MainActivity.VIEW_THREAD_FRAGMENT, 1);
		activeButtonFragmentMapping.put(MainActivity.DIARY_FRAGMENT, 2);
		activeButtonFragmentMapping.put(MainActivity.CREATE_THREAD_FRAGMENT, 3);
		activeButtonFragmentMapping.put(MainActivity.SEARCH_FRAGMENT, 4);
	}

	private MainActivity parentActivity;
	private ImageView[] buttons;
	private int activeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.navigation_menu, container);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = (int)(width*0.87/NUMBER_OF_BUTTONS);
		
		buttons = new ImageView[NUMBER_OF_BUTTONS];
		for (int i = 0; i < buttons.length; i++) {
			final int index = i;
			buttons[i] = (ImageView) view.findViewById(buttonInfo[i].buttonID);
			buttons[i].getLayoutParams().height = height;
			buttons[i].setImageResource(buttonInfo[i].inactiveIconID);
			buttons[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					parentActivity.changeFragment(buttonInfo[index].targetFragment);
				}
			});
		}
		activeButton = -1;
		
		return view;
	}
	
	public void setParentActivity(MainActivity parent) {
		parentActivity = parent;
	}
	
	public void updateNavigationMenu(int fragmentID) {
		if (activeButton >= 0 && activeButton < NUMBER_OF_BUTTONS) {
			buttons[activeButton].setImageResource(buttonInfo[activeButton].inactiveIconID);
		}
		if (activeButtonFragmentMapping.containsKey(fragmentID)) {
			activeButton = activeButtonFragmentMapping.get(fragmentID);
		} else {
			activeButton = -1;
		}
		if (activeButton >= 0 && activeButton < NUMBER_OF_BUTTONS) {
			buttons[activeButton].setImageResource(buttonInfo[activeButton].activeIconID);
		}
	}
	
	private static class NavigationButtonInfo {
		int buttonID, activeIconID, inactiveIconID, targetFragment;
		public NavigationButtonInfo(int buttonID, int activeIconID, int inactiveIconID, int targetFragment) {
			this.buttonID = buttonID;
			this.activeIconID = activeIconID;
			this.inactiveIconID = inactiveIconID;
			this.targetFragment = targetFragment;
		}
	}
}
