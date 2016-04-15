package com.klowerbase.test;

import android.os.Bundle;

import com.klower.titlebar.BaseException;
import com.klower.togglebar.CustomToggleBar;
import com.klower.togglebar.CustomToggleBar.OnBarClicklistener;

public class ToggleBarActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		// TODO Auto-generated method stub
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.togglebar);
		
		CustomToggleBar toggleBar = (CustomToggleBar) findViewById(R.id.togglebar);
		toggleBar.setDisableSelectIndex(2);
		toggleBar.setOnBarClicklistener(new OnBarClicklistener() {
			
			@Override
			public void onBarRepeatClick(int position) {
				// TODO Auto-generated method stub
				toast("Reapeat position: "+ position);
			}
			
			@Override
			public void onBarClick(int position) {
				// TODO Auto-generated method stub
				toast("position: "+ position);
			}
		});
		
		toggleBar.setTabSelected(1);
	}
}
