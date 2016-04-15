package com.klower.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.klower.R;
import com.klower.roundedimageview.RoundedImageView;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GridViewPicAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ArrayList<String> items = new ArrayList<String>();

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	private int logoWidth;

	public GridViewPicAdapter(Context mContext, ArrayList<String> urls,
			int numColumns) {
		super();
		items.clear();
		items.addAll(urls);
		logoWidth = Utils.getScreenWidth((Activity) mContext) / numColumns;
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(this.mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils
				.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
	}
	
	public GridViewPicAdapter(Context mContext, ArrayList<String> urls,
			int numColumns, int photo) {
		super();
		items.clear();
		items.addAll(urls);
		logoWidth = Utils.getScreenWidth((Activity) mContext) / numColumns;
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(this.mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils
				.getNormalDisplayImageOptions(photo);
	}

	public GridViewPicAdapter(Context mContext, int numColumns) {
		super();
		logoWidth = Utils.getScreenWidth((Activity) mContext) / numColumns;
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(this.mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils
				.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
	}
	
	public GridViewPicAdapter(Context mContext, int numColumns, int photo) {
		super();
		logoWidth = Utils.getScreenWidth((Activity) mContext) / numColumns;
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(this.mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils
				.getNormalDisplayImageOptions(photo);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.ui_gridview_item_2,
					null);
			holder.logo = (RoundedImageView) convertView
					.findViewById(R.id.gridview_item_logo);
			LayoutParams layoutParams = holder.logo.getLayoutParams();
			layoutParams.height = logoWidth;
			layoutParams.width = logoWidth;
			holder.logo.setLayoutParams(layoutParams);
			holder.logo.setScaleType(ScaleType.CENTER_CROP);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String str = items.get(position);
		imageLoader.displayImage(str, holder.logo, options);
		return convertView;
	}

	class ViewHolder {
		RoundedImageView logo;

		TextView txt;
	}

	public void clearData() {
		items.clear();
		notifyDataSetChanged();
	}

	public void setData(ArrayList<String> list) {
		if (list != null) {
			items.clear();
			items.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void addData(ArrayList<String> list) {
		if (list != null) {
			items.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void addImgsData(ArrayList<String> list) {
		for (int i = 0; i < items.size(); i++) {
			if ("".equalsIgnoreCase(items.get(i))) {
				items.remove(i);
				break;
			}
		}
		items.addAll(list);
		items.add("");
		notifyDataSetChanged();
	}
	
	public void addData(String item) {
		for (int i = 0; i < items.size(); i++) {
			if ("".equalsIgnoreCase(items.get(i))) {
				items.remove(i);
				break;
			}
		}
		if (!"".equalsIgnoreCase(item)) {
			items.add(item);
		}
		items.add("");
		notifyDataSetChanged();
	}

	public ArrayList<String> getData() {
		return items;
	}

}
