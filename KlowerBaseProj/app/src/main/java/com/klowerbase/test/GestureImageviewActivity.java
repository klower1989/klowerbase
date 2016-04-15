package com.klowerbase.test;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class GestureImageviewActivity extends Activity {
	protected ImageLoader imageLoader;

	protected DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageLoader = ImageLoader.getInstance();
		options = Utils.getNormalDisplayImageOptions(R.drawable.empty_photo);
		setContentView(R.layout.ui_picture_show);
		final PhotoView gestureImageView = (PhotoView) findViewById(R.id.iv_picture_show);
		if (getIntent() != null && getIntent().getStringExtra("url") != null) {
			// imageLoader.displayImage(getIntent().getStringExtra("url"),
			// gestureImageView, options);
			imageLoader.loadImage(getIntent().getStringExtra("url"), options,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							gestureImageView.setImageBitmap(loadedImage);
						}
					});
		}
	}
}
