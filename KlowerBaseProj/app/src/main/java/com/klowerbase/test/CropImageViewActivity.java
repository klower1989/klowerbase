package com.klowerbase.test;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.klower.cropimage.CropImageView;
import com.klower.titlebar.BaseException;

public class CropImageViewActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.cropimage);
		
		final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
		findViewById(R.id.crop).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cropImageView.setImageBitmap(cropImageView.getCroppedImage());
			}
		});
	}
}
