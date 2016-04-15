package com.klowerbase.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.klower.adapter.GestureImagePagerAdapter;
import com.klower.adapter.GestureImagePagerAdapter.ImageClickLisner;
import com.klower.component.MutipleTouchViewPager;
import com.klower.viewpagerindicator.CirclePageIndicator;

public class GestureViewpagerIndicatorActivity extends Activity {
	private MutipleTouchViewPager mImagePager;

	private CirclePageIndicator mCirclePageIndicator;

	private GestureImagePagerAdapter mImagePagerAdapter;

	private ArrayList<String> mImageUrls = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gestureviewpagerindicator);

		mImagePager = (MutipleTouchViewPager) findViewById(R.id.jobdetail_imagepager);
		mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.jobdetail_indicator);

		mImageUrls
				.add("http://e.hiphotos.baidu.com/image/pic/item/a2cc7cd98d1001e9c5dc5c08ba0e7bec55e797d1.jpg");
		mImageUrls
				.add("http://b.hiphotos.baidu.com/image/pic/item/f7246b600c338744222d657b530fd9f9d62aa0c9.jpg");
		mImageUrls
				.add("http://a.hiphotos.baidu.com/image/pic/item/5ab5c9ea15ce36d30decc93738f33a87e850b1c9.jpg");
		mImageUrls
				.add("http://d.hiphotos.baidu.com/image/pic/item/7aec54e736d12f2ebae6e6674dc2d562843568d1.jpg");
		mImageUrls
				.add("http://h.hiphotos.baidu.com/image/pic/item/b8389b504fc2d56282eb98eae51190ef77c66cd1.jpg");
		mImagePagerAdapter = new GestureImagePagerAdapter(this);
		mImagePager.setAdapter(mImagePagerAdapter);
		mImagePagerAdapter.setImagesUrl(mImageUrls);
		mCirclePageIndicator.setViewPager(mImagePager);
		mImagePagerAdapter.setImageClickLisner(new ImageClickLisner() {

			@Override
			public void imageClick(int position) {
				Intent intent = new Intent(GestureViewpagerIndicatorActivity.this,
						GestureImageviewActivity.class);
				intent.putExtra("url", mImagePagerAdapter.getImagesUrl().get(position));
				startActivity(intent);
			}
		});
	}
}
