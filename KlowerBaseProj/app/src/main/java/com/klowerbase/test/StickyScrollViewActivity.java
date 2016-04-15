package com.klowerbase.test;

import android.os.Bundle;

import com.klower.titlebar.BaseActivity;
import com.klower.titlebar.BaseException;

public class StickyScrollViewActivity extends BaseActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		getTitleBar().setCustomTitle("StickyScrollView");
		getTitleBar().setRightTitle("右标题");
		setCustomContentView(R.layout.stickyscrollview);
	}

	@Override
	public void onRightTitleClick() {
		super.onRightTitleClick();
	}

	@Override
	public void onLeftTitleClick() {
		super.onLeftTitleClick();
	}
}
