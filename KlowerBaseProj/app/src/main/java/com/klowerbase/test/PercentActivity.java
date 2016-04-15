package com.klowerbase.test;

import android.os.Bundle;

import com.klower.titlebar.BaseException;

public class PercentActivity extends ActionBarActivity{

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.percent);
	}
	

}
