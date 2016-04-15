package com.klowerbase.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.klower.coverflow.FancyCoverFlow;
import com.klower.coverflow.FancyCoverFlowAdapter;
import com.klowerbase.test.R;

public class CoverFlowAdapter extends FancyCoverFlowAdapter {

	Context mContext;

	int[] drawales = new int[] { R.drawable.bl_landing_unlockbtn,
			R.drawable.bl_landing_lightsbtn, R.drawable.bl_landing_startbtn,
			R.drawable.bl_landing_hornbtn };

	public CoverFlowAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getCoverFlowItem(int position, View reusableView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (reusableView == null) {
			holder = new ViewHolder();
			reusableView = LayoutInflater.from(mContext).inflate(
					R.layout.coverflow_item, null);
			holder.pic = (ImageView) reusableView.findViewById(R.id.pic);
			FancyCoverFlow.LayoutParams layoutParams = new FancyCoverFlow.LayoutParams(
					400, 500);
			reusableView.setLayoutParams(layoutParams);
			reusableView.setTag(holder);
		} else {
			holder = (ViewHolder) reusableView.getTag();
		}
		holder.pic.setImageResource(drawales[position % drawales.length]);
		return reusableView;
	}

	class ViewHolder {
		ImageView pic;
	}

}
