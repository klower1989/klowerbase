package com.klowerbase.test;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.klower.coverflow.FancyCoverFlow;
import com.klower.titlebar.BaseException;
import com.klowerbase.test.adapter.CoverFlowAdapter;

public class CoverFlowActivity extends ActionBarActivity {
	FancyCoverFlow mCoverFlow;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.coverflow);
		mCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
		final CoverFlowAdapter adapter = new CoverFlowAdapter(this);
		mCoverFlow.setAdapter(adapter);
		this.mCoverFlow.setUnselectedAlpha(1.0f);
		this.mCoverFlow.setUnselectedSaturation(0.0f);
		this.mCoverFlow.setUnselectedScale(0.5f);
		this.mCoverFlow.setSpacing(50);
		this.mCoverFlow.setMaxRotation(0);
		this.mCoverFlow.setScaleDownGravity(0.5f);
		this.mCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
		this.mCoverFlow.setSelection(3);
		this.mCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2 == mCoverFlow.getSelectedItemPosition()){
					toast("position: " + arg2);
				}
			}
		});
	}
}
