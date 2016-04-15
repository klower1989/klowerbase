package com.klowerbase.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;

import com.klower.utilities.Utils;
import com.klowerbase.test.R;

import java.util.ArrayList;

public class RecycleviewStagGridAdapter extends Adapter<MyViewHolder> {

	OnItemClickListener mItemClickListener;

	OnItemLongClickListener mItemLongClickListener;

	Context mContext;

	ArrayList<String> items = new ArrayList<>();

	public RecycleviewStagGridAdapter(OnItemClickListener mItemClickListener,
									  OnItemLongClickListener mItemLongClickListener, Context mContext) {
		super();
		this.mItemClickListener = mItemClickListener;
		this.mItemLongClickListener = mItemLongClickListener;
		this.mContext = mContext;
	}

	public void  setData(ArrayList<String> items){
		this.items.clear();
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	public void  addData(ArrayList<String> items){
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int arg1) {
		// TODO Auto-generated method stub
		ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
		params.height = Utils.getDPValue(mContext, 50)+ arg1*50;
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		holder.tv.setLayoutParams(params);
		holder.tv.setBackgroundResource(R.drawable.grey_boder_bg);
		holder.tv.setText(items.get(arg1));
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
