package com.klowerbase.test;

import android.os.Bundle;

import com.klower.slidingmenu.SlidingMenu;
import com.klower.slidingmenu.SlidingMenuActivity;

public class SlidingActivity extends SlidingMenuActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu(savedInstanceState);
	}

	private void initSlidingMenu(Bundle savedInstanceState) {
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// getActionBar().setDisplayHomeAsUpEnabled(true);
		setSlidingActionBarEnabled(true);
		sm.setMode(SlidingMenu.LEFT);
		setContentView(R.layout.sliding_right);
		setBehindContentView(R.layout.sliding_left);
	}
}
