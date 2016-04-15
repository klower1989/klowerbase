package com.klowerbase.test;

import android.app.Activity;
import android.os.Bundle;

import com.klower.horizonallistview.HorizontalListView;
import com.klowerbase.test.adapter.GalleryAdapter;

public class HorizonalListviewActivity extends Activity {

	HorizontalListView mHorizontalListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horizonallistview);
		mHorizontalListView = (HorizontalListView) findViewById(R.id.horizonallistview);
		mHorizontalListView.setAdapter(new GalleryAdapter(this));
	}
}
