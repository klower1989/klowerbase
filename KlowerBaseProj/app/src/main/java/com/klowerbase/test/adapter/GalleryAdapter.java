/**
 * All rights reserved
 */

package com.klowerbase.test.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.klower.roundedimageview.RoundedImageView;
import com.klower.utilities.Utils;
import com.klowerbase.test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2013-11-26
 */
public class GalleryAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	ArrayList<String> imgs = new ArrayList<String>();

	public GalleryAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(this.mContext);
		imageLoader = ImageLoader.getInstance();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = 2;
		options = Utils.getNormalDisplayImageOptions(R.drawable.empty_photo);
		imgs.add("http://e.hiphotos.baidu.com/image/pic/item/a2cc7cd98d1001e9c5dc5c08ba0e7bec55e797d1.jpg");
		imgs.add("http://b.hiphotos.baidu.com/image/pic/item/f7246b600c338744222d657b530fd9f9d62aa0c9.jpg");
		imgs.add("http://a.hiphotos.baidu.com/image/pic/item/5ab5c9ea15ce36d30decc93738f33a87e850b1c9.jpg");
		imgs.add("http://d.hiphotos.baidu.com/image/pic/item/7aec54e736d12f2ebae6e6674dc2d562843568d1.jpg");
		imgs.add("http://h.hiphotos.baidu.com/image/pic/item/b8389b504fc2d56282eb98eae51190ef77c66cd1.jpg");
	}

	@Override
	public int getCount() {
		return imgs.size();
	}

	@Override
	public Object getItem(int position) {
		return imgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoler holer;
		if (convertView == null) {
			holer = new ViewHoler();
			convertView = mLayoutInflater.inflate(R.layout.ui_gridview_item,
					null);
			holer.icon = (RoundedImageView) convertView
					.findViewById(R.id.imageView2);
			holer.icon.setLayoutParams(new LinearLayout.LayoutParams(Utils
					.getScreenWidth((Activity) mContext) / 4, Utils
					.getScreenWidth((Activity) mContext) / 4));
			convertView.setTag(holer);
		} else {
			holer = (ViewHoler) convertView.getTag();
		}
		imageLoader.displayImage(imgs.get(position), holer.icon, options);
		return convertView;
	}

	class ViewHoler {
		RoundedImageView icon;

	}

}
