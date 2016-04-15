/**
 * All rights reserved
 */

package com.klowerbase.test.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.klower.model.SortModel;
import com.klowerbase.test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2013-11-26
 */
public class SortAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	private ArrayList<SortModel> models = new ArrayList<SortModel>();

	public SortAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		return models.size();
	}

	@Override
	public Object getItem(int position) {
		return models.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.sort_item, null);
			holer.sortChar = (TextView) convertView
					.findViewById(R.id.sort_char);
			holer.name = (TextView) convertView.findViewById(R.id.sort_name);
			convertView.setTag(holer);
		} else {
			holer = (ViewHoler) convertView.getTag();
		}
		holer.name.setText(models.get(position).getName());
		int section = getSectionForPosition(position);
		if (position == getPositionForSection(section)) {
			holer.sortChar.setVisibility(View.VISIBLE);
			holer.sortChar.setText(models.get(position).getSortLetters());
		} else {
			holer.sortChar.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHoler {
		TextView sortChar, name;

	}
	
	public void setData(ArrayList<SortModel> list){
		this.models = list;
		notifyDataSetChanged();
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = models.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	public int getSectionForPosition(int position) {
		return models.get(position).getSortLetters().charAt(0);
	}
}
