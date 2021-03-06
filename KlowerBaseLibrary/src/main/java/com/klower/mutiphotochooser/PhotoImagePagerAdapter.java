/**
 * Copyright (c) 2013 .
 * All rights reserved
 */

package com.klower.mutiphotochooser;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.klower.R;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2013-7-12
 */
public class PhotoImagePagerAdapter extends PagerAdapter {

	private ArrayList<PhotoModel> images;

	private LayoutInflater inflater;

	private Context mContext;

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	ImageClickLisner clickLisner;
	
	private boolean isLoadImage = true;

	public PhotoImagePagerAdapter(Context context) {
		this.images = new ArrayList<PhotoModel>();
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
	}
	
	public PhotoImagePagerAdapter(Context context, ArrayList<PhotoModel> images) {
		this.mContext = context;
		this.images = images;
		inflater = LayoutInflater.from(mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
	}
	
	public PhotoImagePagerAdapter(Context context, ArrayList<PhotoModel> images, boolean isShowImage) {
		this.mContext = context;
		this.images = images;
		isLoadImage = isShowImage;
		inflater = LayoutInflater.from(mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
	}

	  public void setLoadImage(boolean isShowImage){
	    	isLoadImage = isShowImage;
	    }

	  
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public void finishUpdate(View container) {
	}

	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, final int position) {
		View imageLayout = inflater.inflate(R.layout.ui_pager_item2, view,
				false);
		final PhotoView imageView = (PhotoView) imageLayout
				.findViewById(R.id.image);
		final ProgressBar spinner = (ProgressBar) imageLayout
				.findViewById(R.id.loading);
		if(isLoadImage){
			imageLoader.loadImage("file://"+images.get(position).getOriginalPath(), options,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							spinner.setVisibility(View.VISIBLE);
							super.onLoadingStarted(imageUri, view);
						}

						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							spinner.setVisibility(View.GONE);
							imageView.setImageBitmap(loadedImage);
						}
					});

		}else{
			imageView.setImageResource(R.drawable.lh_photo_default);
		}

		
		((ViewPager) view).addView(imageLayout, 0);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (clickLisner != null) {
					clickLisner.imageClick(position);
				}
			}
		});
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View container) {
	}

	public void setImagesUrl(ArrayList<PhotoModel> imageUrls) {
		images = imageUrls;
		notifyDataSetChanged();
	}

	public ArrayList<PhotoModel> getImagesUrl() {
		return images;
	}

	public void setImageClickLisner(ImageClickLisner imageClickLisner) {
		clickLisner = imageClickLisner;
	}

	public interface ImageClickLisner {
		public void imageClick(int position);
	}
	
	public ArrayList<PhotoModel> getSelectedImgs() {
		ArrayList<PhotoModel> imgs = new ArrayList<PhotoModel>();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).isChecked()) {
				imgs.add(images.get(i));
			}
		}
		return imgs;
	}
	
	public ArrayList<String> getSelectedPhotos() {
		ArrayList<String> imgs = new ArrayList<String>();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).isChecked()) {
				imgs.add(images.get(i).getOriginalPath());
			}
		}
		return imgs;
	}
	

}
