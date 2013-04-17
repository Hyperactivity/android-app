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
	
	private static int[] active = new int[] {R.drawable.active_house, R.drawable.active_bubble, R.drawable.active_star, R.drawable.active_search, R.drawable.active_search};
	private static int[] inactive = new int[] {R.drawable.non_active_house, R.drawable.non_active_bubble, R.drawable.non_active_star, R.drawable.non_active_search, R.drawable.non_active_search};
	
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
		buttons[0] = (ImageView) view.findViewById(R.id.navigation_menu_button_1);
		buttons[1] = (ImageView) view.findViewById(R.id.navigation_menu_button_2);
		buttons[2] = (ImageView) view.findViewById(R.id.navigation_menu_button_3);
		buttons[3] = (ImageView) view.findViewById(R.id.navigation_menu_button_4);
		buttons[4] = (ImageView) view.findViewById(R.id.navigation_menu_button_5);
		for (int i = 0; i < buttons.length; i++) {
			final int index = i;
			ImageView button = buttons[i];
			button.getLayoutParams().height = height;
			button.setImageResource(inactive[i]);
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
				buttons[activeButton].setImageResource(inactive[activeButton]);
			}
			buttons[index].setImageResource(active[index]);
			activeButton = index;
		}
	}
}
