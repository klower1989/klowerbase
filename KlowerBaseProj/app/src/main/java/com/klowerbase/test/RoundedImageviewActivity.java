package com.klowerbase.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.klower.roundedimageview.RoundedImageView;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class RoundedImageviewActivity extends Activity {

	RoundedImageView mImageView, mImageView2,mImageView3;

	protected ImageLoader imageLoader;

	protected DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roundedimageview);

		mImageView = (RoundedImageView) findViewById(R.id.imageView1);
		mImageView2 = (RoundedImageView) findViewById(R.id.imageView2);
		mImageView3= (RoundedImageView) findViewById(R.id.imageView3);

		imageLoader = ImageLoader.getInstance();
		options = Utils.getNormalDisplayImageOptions(R.drawable.empty_photo);
		imageLoader
				.displayImage(
						"http://a.hiphotos.baidu.com/image/pic/item/6f061d950a7b020843aee2e060d9f2d3572cc848.jpg",
						mImageView, options);
		imageLoader
				.displayImage(
						"http://a.hiphotos.baidu.com/image/pic/item/6f061d950a7b020843aee2e060d9f2d3572cc848.jpg",
						mImageView2, options);
		imageLoader
		.displayImage(
				"http://a.hiphotos.baidu.com/image/pic/item/6f061d950a7b020843aee2e060d9f2d3572cc848.jpg",
				mImageView3, options);
	}

}
