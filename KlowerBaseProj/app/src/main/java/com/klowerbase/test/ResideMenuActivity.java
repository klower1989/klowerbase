package com.klowerbase.test;

import android.os.Bundle;
import android.view.MotionEvent;

import com.klower.residemenu.ResideMenu;
import com.klower.residemenu.ResideMenu.OnMenuListener;
import com.klower.residemenu.ResideMenuItem;
import com.klower.titlebar.BaseException;

public class ResideMenuActivity extends ActionBarActivity implements
		OnMenuListener {
	private ResideMenu resideMenu;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		getTitleBar().setRightTitle("Menu");
		getTitleBar().setLeftTitle("Menu");
		setCustomContentView(R.layout.reside_menu);
		setUpMenu();
	}

	private void setUpMenu() {
		resideMenu = new ResideMenu(this);
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
		resideMenu.setBackground(R.drawable.empty_photo);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(this);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);
		resideMenu.setLeftMenuView(R.layout.mail);

		resideMenu.addMenuItem(new ResideMenuItem(this, R.drawable.ic_launcher,
				"menu"), ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(new ResideMenuItem(this, R.drawable.ic_launcher,
				"menu"), ResideMenu.DIRECTION_RIGHT);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onLeftTitleClick() {
		resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
	}

	@Override
	public void onRightTitleClick() {
		resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
	}

	@Override
	public void openMenu() {
		toast("openMenu");
	}

	@Override
	public void closeMenu() {
		toast("closeMenu");
	}
}
