package com.klower.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.klower.R;
import com.klower.mutiphotochooser.OnPhotoChooseListener;
import com.klower.mutiphotochooser.PhotoModel;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChoosePhotoAdapter extends BaseAdapter {

	private ArrayList<PhotoModel> photos = new ArrayList<PhotoModel>();

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	private OnPhotoChooseListener mChooseListener;

	/**
	 * 1: Single, 2:Muti
	 */
	private int choosePhotoType = 1;

	public ChoosePhotoAdapter(Context context) {
		super();
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils
				.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
	}

	public void setOnPhotoChooseListener(OnPhotoChooseListener listener) {
		mChooseListener = listener;
	}

	public int getChoosePhotoType() {
		return choosePhotoType;
	}

	public void setChoosePhotoType(int choosePhotoType) {
		this.choosePhotoType = choosePhotoType;
	}

	@Override
	public int getCount() {
		return photos.size();
	}

	@Override
	public Object getItem(int position) {
		return photos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getItemWidth() {
		int horizentalSpace = mContext.getResources().getDimensionPixelSize(
				R.dimen.sticky_item_horizontalSpacing);
		return (Utils.getScreenWidth(mContext) - (horizentalSpace * (3 - 1))) / 3;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					R.layout.choosephoto_photo_item, null);
			holder.image = (ImageView) convertView
					.findViewById(R.id.iv_photo_lpsi);
			LayoutParams layoutParams = holder.image.getLayoutParams();
			layoutParams.height = getItemWidth();
			layoutParams.width = getItemWidth();
			holder.image.setLayoutParams(layoutParams);
			holder.image.setScaleType(ScaleType.CENTER_CROP);

			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.cb_photo_lpsi);
			holder.cover = (ImageView) convertView
					.findViewById(R.id.cb_photo_cover);
			holder.cover.setLayoutParams(layoutParams);
			holder.cover.setScaleType(ScaleType.FIT_XY);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PhotoModel model = photos.get(position);
//		holder.checkBox
//				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView,
//							boolean isChecked) {
//						photos.get(position).setChecked(isChecked);
//						if (mChooseListener != null) {
//							mChooseListener.onChoose(getSelectedImgs());
//						}
//					}
//				});
		holder.cover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(photos.get(position).isChecked()){
					holder.checkBox.setChecked(false);
					holder.cover.setBackgroundColor(Color.parseColor("#00000000"));
					photos.get(position).setChecked(false);
				}else{
					holder.checkBox.setChecked(true);
					holder.cover.setBackgroundColor(Color.parseColor("#70000000"));
					if(choosePhotoType == 1){
						clearChcked();
						photos.get(position).setChecked(true);
						notifyDataSetChanged();
					}else{
						photos.get(position).setChecked(true);
					}

				}
				if (mChooseListener != null) {
					mChooseListener.onChoose(getSelectedImgs());
				}
			}
		});
		holder.checkBox.setChecked(model.isChecked());
		if(model.isChecked()){
			holder.cover.setBackgroundColor(Color.parseColor("#70000000"));
		}else{
			holder.cover.setBackgroundColor(Color.parseColor("#00000000"));
		}
		imageLoader.displayImage("file://" + model.getOriginalPath(),
				holder.image, options);

		return convertView;
	}

	class ViewHolder {
		ImageView image, cover;

		CheckBox checkBox;
	}

	public void setData(ArrayList<PhotoModel> items) {
		this.photos.clear();
		this.photos.addAll(items);
		notifyDataSetChanged();
	}

	public ArrayList<String> getSelectedPhotos() {
		ArrayList<String> imgs = new ArrayList<String>();
		for (int i = 0; i < photos.size(); i++) {
			if (photos.get(i).isChecked()) {
				imgs.add(photos.get(i).getOriginalPath());
			}
		}
		return imgs;
	}

	public void clearChcked(){
		for (int i = 0; i < photos.size(); i++) {
			photos.get(i).setChecked(false);
		}
	}
	
	public ArrayList<PhotoModel> getSelectedImgs() {
		ArrayList<PhotoModel> imgs = new ArrayList<PhotoModel>();
		for (int i = 0; i < photos.size(); i++) {
			if (photos.get(i).isChecked()) {
				imgs.add(photos.get(i));
			}
		}
		return imgs;
	}

	public boolean isChoosedPhoto() {
		return getSelectedPhotos().isEmpty();
	}

}
