package com.klowerbase.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.klowerbase.test.R;

public class RecycleviewAdapter extends Adapter<MyViewHolder> {

	OnItemClickListener mItemClickListener;

	OnItemLongClickListener mItemLongClickListener;

	Context mContext;

	public RecycleviewAdapter(OnItemClickListener mItemClickListener,
			OnItemLongClickListener mItemLongClickListener, Context mContext) {
		super();
		this.mItemClickListener = mItemClickListener;
		this.mItemLongClickListener = mItemLongClickListener;
		this.mContext = mContext;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int arg1) {
		// TODO Auto-generated method stub
		holder.tv.setText(" position: " + arg1);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext)
				.inflate(R.layout.recycle_item, null), mItemClickListener,
				mItemLongClickListener);
		return holder;
	}

}
