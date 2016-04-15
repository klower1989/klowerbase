package com.klowerbase.test;

import android.os.Bundle;

import com.klower.titlebar.BaseActivity;
import com.klower.titlebar.BaseException;
import com.klower.utilities.Utils;

public class ActionBarActivity extends BaseActivity{

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		getTitleBar().setCustomTitle("Hello");
		getTitleBar().setRightTitle("右标题");
	}
	
	@Override
	public void onRightTitleClick() {
		super.onRightTitleClick();
		toast("onRightTitleClick");
	}
	
	@Override
	public void onLeftTitleClick() {
		super.onLeftTitleClick();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		if(Utils.isBackground(this)){
//			toast("程序在后台运行！");
//		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	}
}
