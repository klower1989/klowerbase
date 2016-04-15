package com.klower.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.klower.R;
import com.klower.roundedimageview.RoundedImageView;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GridViewPicsAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ArrayList<Bitmap> items = new ArrayList<Bitmap>();

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	private int logoWidth;

	private int limitSize = 4;

	public GridViewPicsAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		logoWidth = Utils.getScreenWidth((Activity) mContext) / 5;
		mLayoutInflater = LayoutInflater.from(this.mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils
				.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
	}

	public void setLimitSize(int size) {
		limitSize = size;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.ui_gridview_item_2,
					null);
			holder.delete = (ImageView) convertView
					.findViewById(R.id.gridview_item_delete);
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
		Bitmap bitmap = items.get(position);
		if (bitmap == null) {
			holder.delete.setVisibility(View.GONE);
			holder.logo.setImageResource(R.drawable.lh_advice_default);
		} else {
			holder.delete.setVisibility(View.VISIBLE);
			holder.logo.setImageBitmap(bitmap);
		}
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				items.remove(position);
				if (!isHasNull()) {
					items.add(null);
				}
				notifyDataSetChanged();
			}
		});
		// imageLoader.displayImage(str, holder.logo, options);
		return convertView;
	}

	class ViewHolder {
		RoundedImageView logo;

		TextView txt;

		ImageView delete;
	}

	public void clearData() {
		items.clear();
		notifyDataSetChanged();
	}

	public void setData(ArrayList<Bitmap> list) {
		if (list != null) {
			items.clear();
			items.addAll(list);
			notifyDataSetChanged();
		}
	}

	// public void addData(ArrayList<Bitmap> list) {
	// if (list != null) {
	// items.addAll(list);
	// notifyDataSetChanged();
	// }
	// }

	public void addData(Bitmap item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == null) {
				items.remove(i);
				break;
			}
		}
		if (item != null) {
			if (items.size() != limitSize) {
				items.add(item);
			} else {
				notifyDataSetChanged();
				return;
			}

		}
		if (items.size() == limitSize) {

		} else {
			items.add(null);
		}
		notifyDataSetChanged();
	}

	private boolean isHasNull() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == null) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Bitmap> getData() {
		return items;
	}

}
