/**
 * Copyright (c) 2013 .
 * All rights reserved
 */

package com.klower.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

import com.klower.R;
import com.klower.utilities.LogTool;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2013-7-12
 */
public class ImagePagerAdapter extends PagerAdapter {

    private ArrayList<String> images;

    private LayoutInflater inflater;

    private Context mContext;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    ImageClickLisner clickLisner;
    
    String mSharePath;
    
    private ScaleType mScaleType = ScaleType.CENTER_CROP;
    
    private boolean isLoadImage = true;

    public ImagePagerAdapter(Context context) {
        this.images = new ArrayList<String>();
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        imageLoader = ImageLoader.getInstance();
        options = Utils.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
    }
    
    public ImagePagerAdapter(Context context, ArrayList<String> urls) {
        this.images = urls;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        imageLoader = ImageLoader.getInstance();
        options = Utils.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
    }
    
    public void setImageScaleType(ScaleType scaleType){
    	mScaleType = scaleType;
    }
    
    public void setLoadImage(boolean isShowImage){
    	isLoadImage = isShowImage;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
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
        View imageLayout = inflater.inflate(R.layout.ui_pager_item, view, false);
        ImageView imageView = (ImageView)imageLayout.findViewById(R.id.image);
        imageView.setScaleType(mScaleType);
        final ProgressBar spinner = (ProgressBar)imageLayout.findViewById(R.id.loading);
        if(isLoadImage){
        	 imageLoader.displayImage(images.get(position), imageView, options,
                     new SimpleImageLoadingListener() {
                         @Override
                         public void onLoadingStarted(String imageUri, View view) {
                             spinner.setVisibility(View.VISIBLE);
                         }

                         @Override
                         public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                             String message = null;
                             switch (failReason.getType()) {
                                 case IO_ERROR:
                                     message = "Input/Output error";
                                     break;
                                 case DECODING_ERROR:
                                     message = "Image can't be decoded";
                                     break;
                                 case NETWORK_DENIED:
                                     message = "Downloads are denied";
                                     break;
                                 case OUT_OF_MEMORY:
                                     message = "Out Of Memory error";
                                     break;
                                 case UNKNOWN:
                                     message = "Unknown error";
                                     break;
                             }
                             LogTool.e("image", "error msg: " + message);
                             // Toast.makeText(mContext, message,
                             // Toast.LENGTH_SHORT).show();

                             spinner.setVisibility(View.GONE);
                         }

                         @Override
                         public void onLoadingComplete(String imageUri, View view,
                         		Bitmap loadedImage) {
                         	// TODO Auto-generated method stub
                         	super.onLoadingComplete(imageUri, view, loadedImage);
                         	spinner.setVisibility(View.GONE);
                         }
                     });
        }else{
        	imageView.setImageResource(R.drawable.lh_photo_default);
        }
       

        ((ViewPager)view).addView(imageLayout, 0);
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

    public void setImagesUrl(ArrayList<String> imageUrls) {
        images = imageUrls;
        notifyDataSetChanged();
    }
    
    public ArrayList<String> getImagesUrl() {
        return images;
    }

    public void setImageClickLisner(ImageClickLisner imageClickLisner) {
        clickLisner = imageClickLisner;
    }

    public interface ImageClickLisner {
        public void imageClick(int position);
    }
    
}
