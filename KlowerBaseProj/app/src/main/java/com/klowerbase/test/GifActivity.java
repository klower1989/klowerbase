package com.klowerbase.test;

import android.os.Bundle;
import android.os.Handler;

import com.klower.gif.GifAnimationListener;
import com.klower.gif.GifView;
import com.klower.titlebar.BaseException;

public class GifActivity extends ActionBarActivity {

	private GifView mGifView;
	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.gif);
		
		mGifView = (GifView) findViewById(R.id.gifimage);
		mGifView.setGifImage(R.drawable.test_gif, GifView.K_AC);
		mGifView.setGifAnimationListener(new GifAnimationListener() {
			
			@Override
			public void onGifAnimationEnd() {
				if (mGifView != null) {
		        	mGifView.stopGifAnimation();
		        }
			}
		});
		
		 new Handler().postDelayed(new Runnable() {
	            
	            @Override
	            public void run() {
	            	mGifView.startGifAnimation();
	            }
	        }, 1000);
	}
	
	 @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        if (mGifView != null) {
	        	mGifView.stopGifAnimation();
	        }
	    }
}
