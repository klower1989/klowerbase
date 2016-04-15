package com.klower.adapter;

import com.klower.R;
import com.klower.utilities.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PINAdapter extends BaseAdapter {
	
	private Context mContext;
	
	private LayoutInflater mLayoutInflater;
	
	public PINAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		return 12;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mLayoutInflater.inflate(R.layout.ui_pin_item, null);
		}
		TextView key = (TextView) convertView.findViewById(R.id.pin_item_key);
		LayoutParams layoutParams = key.getLayoutParams();
		layoutParams.width = Utils.getScreenWidth(mContext)/3;
		layoutParams.height = Utils.getScreenWidth(mContext)/6;
		key.setLayoutParams(layoutParams);
		if(position == 9){
			key.setText("Cancel");
		}else if(position == 10){
			key.setText("0");
		}else if(position == 11){
			key.setText("Delete");
		}else{
			key.setText(""+(position+1));
		
		}
		return convertView;
	}

}
