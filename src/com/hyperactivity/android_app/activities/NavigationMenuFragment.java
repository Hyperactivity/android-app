package com.hyperactivity.android_app.activities;

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
	
	static {
		buttonInfo = new NavigationButtonInfo[NUMBER_OF_BUTTONS];
		buttonInfo[0] = new NavigationButtonInfo(R.id.navigation_menu_button_1, R.drawable.active_house, R.drawable.non_active_house, MainActivity.HOME_FRAGMENT);
		buttonInfo[1] = new NavigationButtonInfo(R.id.navigation_menu_button_2, R.drawable.active_bubble, R.drawable.non_active_bubble, MainActivity.FORUM_FRAGMENT);
		buttonInfo[2] = new NavigationButtonInfo(R.id.navigation_menu_button_3, R.drawable.active_diary, R.drawable.non_active_diary, MainActivity.DIARY_FRAGMENT);
		buttonInfo[3] = new NavigationButtonInfo(R.id.navigation_menu_button_4, R.drawable.active_pen, R.drawable.non_active_pen, MainActivity.FORUM_FRAGMENT);
		buttonInfo[4] = new NavigationButtonInfo(R.id.navigation_menu_button_5, R.drawable.active_search, R.drawable.non_active_search, MainActivity.FORUM_FRAGMENT);
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
					setActiveButton(index);
				}
			});
		}
		activeButton = -1;
		setActiveButton(0);
		
		return view;
	}
	
	public void setParentActivity(MainActivity parent) {
		parentActivity = parent;
	}
	
	private void setActiveButton(int index) {
		if (index != activeButton) {
			if (activeButton != -1) {
				buttons[activeButton].setImageResource(buttonInfo[activeButton].inactiveIconID);
			}
			buttons[index].setImageResource(buttonInfo[index].activeIconID);
			activeButton = index;
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
