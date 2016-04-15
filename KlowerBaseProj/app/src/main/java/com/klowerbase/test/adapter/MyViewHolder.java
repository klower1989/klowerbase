package com.klowerbase.test.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.klowerbase.test.R;

public class MyViewHolder extends ViewHolder implements OnClickListener,
		OnLongClickListener {

	public TextView tv;

	OnItemClickListener mItemClickListener;

	OnItemLongClickListener mItemLongClickListener;

	public MyViewHolder(View arg0, OnItemClickListener onItemClickListener,
			OnItemLongClickListener onItemLongClickListener) {
		super(arg0);

		tv = (TextView) arg0.findViewById(R.id.recycle_item_tv);

		mItemClickListener = onItemClickListener;
		mItemLongClickListener = onItemLongClickListener;

		arg0.setOnClickListener(this);
		arg0.setOnLongClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (mItemClickListener != null) {
			mItemClickListener.onItemClick(null, v, getPosition(), getItemId());
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if (mItemLongClickListener != null) {
			mItemLongClickListener.onItemLongClick(null, v, getPosition(),
					getItemId());
		}
		return true;
	}

}