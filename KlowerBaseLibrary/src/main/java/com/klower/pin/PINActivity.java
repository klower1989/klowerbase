package com.klower.pin;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.klower.R;
import com.klower.adapter.PINAdapter;
import com.klower.titlebar.BaseActivity;
import com.klower.titlebar.BaseException;

public class PINActivity extends BaseActivity {

	GridView mGridView;

	ArrayList<String> pins = new ArrayList<String>();
	
	CustomPinShowView mPinShowView;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		// TODO Auto-generated method stub
		super.onCreateEqually(savedInstanceState);

		setCustomContentView(R.layout.pin);
		mGridView = (GridView) findViewById(R.id.pin_gridview);
		mPinShowView = (CustomPinShowView) findViewById(R.id.pin_showview);
		mGridView.setAdapter(new PINAdapter(this));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 9) {
					finish();
				} else if (position == 11) {
					if (pins.size() > 0) {
						pins.remove(pins.size() - 1);
					}
				} else if (position == 10) {
					if(pins.size() <=4){
						pins.add("0");
					}
					
				} else {
					if(pins.size() <=4){
						pins.add((position + 1) + "");
					}
				}
				mPinShowView.changeStatus(pins.size());
				if(pins.size() ==4){
					toast(pins.toString());
					finish();
				}
				
			}
		});
	}

}
