package com.klower.mutiphotochooser;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klower.R;
import com.klower.component.MutipleTouchViewPager;
import com.klower.titlebar.BaseActivity;
import com.klower.titlebar.BaseException;
import com.klower.viewpagerindicator.CirclePageIndicator;

public class ChoosePhotoScanActivity extends BaseActivity implements OnClickListener {
	private MutipleTouchViewPager mImagePager;

	private CirclePageIndicator mCirclePageIndicator;

	private PhotoImagePagerAdapter mImagePagerAdapter;

	private ArrayList<PhotoModel> mImageUrls = new ArrayList<PhotoModel>();

	private int selectedPosition = 0;
	
	private RelativeLayout mRelativeLayout;
	
	private TextView mConfirm;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.choosephoto_scan);
		if (getIntent() != null) {
			mImageUrls.addAll((List<PhotoModel>) (getIntent()
					.getSerializableExtra("imgs")));
			selectedPosition = getIntent().getIntExtra("position", 0);
		}
		getTitleBar().setRightTitleIcon(R.drawable.ic_check_box_white_24dp);
		getTitleBar().setCustomTitleColor(Color.WHITE);
		getTitleBar().setCustomTitle(
				(selectedPosition + 1) + "/" + mImageUrls.size());

		mRelativeLayout = (RelativeLayout) findViewById(R.id.choosephoto_scanlayout);
		mRelativeLayout.setVisibility(View.VISIBLE);
		mConfirm = (TextView) findViewById(R.id.choosephoto_confirm);
		mConfirm.setTextColor(Color.WHITE);
		mConfirm.setBackgroundResource(R.drawable.blue_bg_selector);
		mConfirm.setClickable(true);
		mConfirm.setText("确定(" + mImageUrls.size() + ")");
		mConfirm.setOnClickListener(this);
		
		mImagePager = (MutipleTouchViewPager) findViewById(R.id.jobdetail_imagepager);
		mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.jobdetail_indicator);

		mImagePagerAdapter = new PhotoImagePagerAdapter(this);
		mImagePager.setAdapter(mImagePagerAdapter);
		mImagePagerAdapter.setImagesUrl(mImageUrls);
		mCirclePageIndicator.setViewPager(mImagePager);
		mCirclePageIndicator.setCurrentItem(selectedPosition);
		mCirclePageIndicator
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						// TODO Auto-generated method stub
						selectedPosition = arg0;
						getTitleBar().setCustomTitle(
								(arg0 + 1) + "/" + mImageUrls.size());

						if (mImagePagerAdapter.getImagesUrl()
								.get(selectedPosition).isChecked()) {
							getTitleBar().setRightTitleIcon(
									R.drawable.ic_check_box_white_24dp);
							
						} else {
							getTitleBar()
							.setRightTitleIcon(
									R.drawable.ic_check_box_outline_blank_white_24dp);
						}

					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onRightTitleClick() {
		if (mImagePagerAdapter.getImagesUrl().get(selectedPosition).isChecked()) {
			getTitleBar().setRightTitleIcon(
					R.drawable.ic_check_box_outline_blank_white_24dp);
			mImagePagerAdapter.getImagesUrl().get(selectedPosition)
					.setChecked(false);
		} else {
			getTitleBar().setRightTitleIcon(R.drawable.ic_check_box_white_24dp);
			mImagePagerAdapter.getImagesUrl().get(selectedPosition)
					.setChecked(true);
		}
		

		if (mImagePagerAdapter.getSelectedImgs().size() > 0) {
			mConfirm.setTextColor(Color.WHITE);
			mConfirm.setBackgroundResource(R.drawable.blue_bg_selector);
			mConfirm.setClickable(true);
			mConfirm.setText("确定(" + mImagePagerAdapter.getSelectedImgs().size() + ")");
		} else {
			mConfirm.setTextColor(R.color.TextGray);
			mConfirm.setBackgroundResource(R.drawable.grey_bg_selector);
			mConfirm.setClickable(false);
			mConfirm.setText("确定");

		}
	
	}

	@Override
	public void onClick(View v) {
		if (mImagePagerAdapter.getSelectedPhotos() != null
				&& mImagePagerAdapter.getSelectedPhotos().size() > 0) {
			Intent intent = new Intent();
			intent.putExtra("photos", mImagePagerAdapter.getSelectedPhotos());
			setResult(RESULT_OK, intent);
			finish();
		}
	}

}
