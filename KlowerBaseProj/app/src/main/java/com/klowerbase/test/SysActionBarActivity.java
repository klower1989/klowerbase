package com.klowerbase.test;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;

public class SysActionBarActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
//		getSupportActionBar().setCustomView(
//				LayoutInflater.from(this).inflate(R.layout.ui_common_title,
//						null), layoutParams);
	}
}
