package com.hyperactivity.android_app.activities;

import com.hyperactivity.android_app.R;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class NavigationMenuFragment extends Fragment {
	
	public static final int NUMBER_OF_BUTTONS = 5;
	
	private static int[] active = new int[] {R.drawable.active_house, R.drawable.active_bubble, R.drawable.active_star, R.drawable.active_search, R.drawable.active_search};
	private static int[] inactive = new int[] {R.drawable.non_active_house, R.drawable.non_active_bubble, R.drawable.non_active_star, R.drawable.non_active_search, R.drawable.non_active_search};
	
	private ImageButton[] buttons;
	private int activeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.navigation_menu, container);
		
		buttons = new ImageButton[NUMBER_OF_BUTTONS];
		buttons[0] = (ImageButton) view.findViewById(R.id.navigation_menu_button_1);
		buttons[1] = (ImageButton) view.findViewById(R.id.navigation_menu_button_2);
		buttons[2] = (ImageButton) view.findViewById(R.id.navigation_menu_button_3);
		buttons[3] = (ImageButton) view.findViewById(R.id.navigation_menu_button_4);
		buttons[4] = (ImageButton) view.findViewById(R.id.navigation_menu_button_5);
		for (int i = 0; i < buttons.length; i++) {
			final int index = i;
			ImageButton button = buttons[i];
			button.setBackgroundResource(inactive[i]);
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setActiveButton(index);
				}
			});
		}
		activeButton = -1;
		setActiveButton(0);
		
		return view;
	}
	
	public void setActiveButton(int index) {
		if (index != activeButton) {
			if (activeButton != -1) {
				buttons[activeButton].setBackgroundResource(inactive[activeButton]);
			}
			buttons[index].setBackgroundResource(active[index]);
			activeButton = index;
		}
	}
}
