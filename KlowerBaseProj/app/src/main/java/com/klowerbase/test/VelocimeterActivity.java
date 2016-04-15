package com.klowerbase.test;

import android.os.Bundle;
import android.widget.SeekBar;

import com.klower.titlebar.BaseException;
import com.klower.velocimeter.VelocimeterView;

public class VelocimeterActivity extends ActionBarActivity {
	private SeekBar seek;
	private VelocimeterView velocimeter;
	private VelocimeterView velocimeter2;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		// TODO Auto-generated method stub
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.velocimeter);

		seek = (SeekBar) findViewById(R.id.seek);
		seek.setOnSeekBarChangeListener(new SeekListener());
		velocimeter = (VelocimeterView) findViewById(R.id.velocimeter);
		velocimeter2 = (VelocimeterView) findViewById(R.id.velocimeter2);
	}

	private class SeekListener implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			velocimeter.setValue(progress, true);
			velocimeter2.setValue(progress, true);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// Empty
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// Empty
		}
	}
}
