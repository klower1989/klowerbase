package com.klower.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.klower.R;
import com.klower.mutiphotochooser.AlbumModel;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChoosePhotoAlbumAdapter extends BaseAdapter {

	private ArrayList<AlbumModel> photos = new ArrayList<AlbumModel>();

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	public ChoosePhotoAlbumAdapter(Context context) {
		super();
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		imageLoader = ImageLoader.getInstance();
		options = Utils
				.getNormalDisplayImageOptions(R.drawable.lh_photo_default);
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
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					R.layout.choosephoto_album_item, null);
			holder.logo = (ImageView) convertView
					.findViewById(R.id.album_item_logo);
			holder.tv = (TextView) convertView.findViewById(R.id.album_item_tx);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AlbumModel albumModel = photos.get(position);
		imageLoader.displayImage("file://" + albumModel.getRecent(),
				holder.logo, options);
		holder.tv.setText(albumModel.getName() + "(" + albumModel.getCount()
				+ ")");
		return convertView;
	}

	class ViewHolder {
		ImageView logo;

		TextView tv;
	}

	public void setData(ArrayList<AlbumModel> items) {
		this.photos.clear();
		this.photos.addAll(items);
		notifyDataSetChanged();
	}
	
	public ArrayList<AlbumModel> getData(){
		return this.photos;
	}

}
