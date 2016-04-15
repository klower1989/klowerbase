package com.klowerbase.test;

import android.os.Bundle;
import android.view.View;

import com.klower.titlebar.BaseException;

public class UITraPullRefreshActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.uitrapullrefresh);
		
//		pullFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.pulllayout);
////		PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
////		// the following are default settings
////		pullFrameLayout.setHeaderView(header);
//
//		  // the following are default settings
//		pullFrameLayout.setResistance(1.7f);
//		pullFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//		pullFrameLayout.setDurationToClose(200);
//		pullFrameLayout.setDurationToCloseHeader(1000);
//        // default is false
//		pullFrameLayout.setPullToRefresh(false);
//		pullFrameLayout.setLastUpdateTimeRelateObject(this);
//        // default is true
//		pullFrameLayout.setKeepHeaderWhenRefresh(true);
//		pullFrameLayout.setPtrHandler(new PtrHandler() {
//		    @Override
//		    public void onRefreshBegin(PtrFrameLayout frame) {
//		        frame.postDelayed(new Runnable() {
//		            @Override
//		            public void run() {
//		            	pullFrameLayout.refreshComplete();
//		            }
//		        }, 3000);
//		    }
//
//		    @Override
//		    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//		        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
//		    }
//		});
		
	}
}
