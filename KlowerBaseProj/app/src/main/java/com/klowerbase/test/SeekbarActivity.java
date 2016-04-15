package com.klowerbase.test;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klower.rangeseekbar.RangeBar;
import com.klower.rangeseekbar.RangeBar.OnRangeBarChangeListener;
import com.klower.seekbar.RangeSeekBar;
import com.klower.seekbar.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.klower.titlebar.BaseException;

public class SeekbarActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.seekbar);
		
		  final TextView min = (TextView) findViewById(R.id.minValue);
	        final TextView max = (TextView) findViewById(R.id.maxValue);
	        
	     // create RangeSeekBar as Integer range between 20 and 75
	        RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(60, 140, this);
	        seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
	                @Override
	                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
	                        // handle changed range values
	                        Log.i("onRangeSeekBarValuesChanged", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
	                        
	                        min.setText(minValue.toString());
	                        max.setText(maxValue.toString());
	                        
	                }
	        });

	        // add RangeSeekBar to pre-defined layout
	        ViewGroup layout = (ViewGroup) findViewById(R.id.layout);
	        layout.addView(seekBar);
	        
	        RangeBar rangeBar = (RangeBar) findViewById(R.id.rangebar);
	        rangeBar.setTickCount(7);
	        rangeBar.setTickHeight(10);
//	        rangeBar.setThumbRadius(10);
//	        rangeBar.setThumbColorNormal(Color.GREEN);
//	        rangeBar.setThumbColorPressed(Color.RED);
	        rangeBar.setThumbImageNormal(R.drawable.seek_thumb_normal);
	        rangeBar.setThumbImagePressed(R.drawable.seek_thumb_pressed);
	        rangeBar.setBarColor(Color.BLACK);
	        rangeBar.setConnectingLineColor(Color.BLUE);
	        rangeBar.setConnectingLineWeight(1);
	        rangeBar.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
				
				@Override
				public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex,
						int rightThumbIndex) {
					// TODO Auto-generated method stub
					toast("leftThumbIndex: "+leftThumbIndex+"---rightThumbIndex: "+rightThumbIndex);
				}
			});
	}
}
