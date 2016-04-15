package com.klowerbase.test;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.klower.sound.SoundMeter;

public class SoundRecordActivity extends Activity {

	private TextView mRecordBtn;

	private ImageView volume;

	private SoundMeter mSensor;

	private Handler mHandler = new Handler();

	private String voiceName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_record);
		mSensor = new SoundMeter();
		mRecordBtn = (TextView) findViewById(R.id.record);
		volume = (ImageView) findViewById(R.id.volume);
		mRecordBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (!Environment.getExternalStorageDirectory().exists()) {
					Toast.makeText(SoundRecordActivity.this, "No SDCard",
							Toast.LENGTH_LONG).show();
					return false;
				}
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mRecordBtn.setText("录音中。。。");
					mRecordBtn.setBackgroundResource(R.drawable.light_blue_more_bottom_bg);
					voiceName = System.currentTimeMillis() + ".amr";
					start(Environment.getExternalStorageDirectory()+"/"+voiceName);
					return true;
				case MotionEvent.ACTION_UP:
					mRecordBtn.setText("开始");
					mRecordBtn.setBackgroundResource(R.drawable.light_blue_bottom_bg);
					stop();
					break;
				}
				return false;
			}
		});
	}

	private static final int POLL_INTERVAL = 300;

	private Runnable mSleepTask = new Runnable() {
		public void run() {
			stop();
		}
	};
	private Runnable mPollTask = new Runnable() {
		public void run() {
			double amp = mSensor.getAmplitude();
			updateDisplay(amp);
			mHandler.postDelayed(mPollTask, POLL_INTERVAL);

		}
	};

	private void start(String name) {
		mSensor.start(name, new MediaRecorder());
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
	}

	private void stop() {
		mHandler.removeCallbacks(mSleepTask);
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
		volume.setImageResource(R.drawable.amp1);
		Intent intent = new Intent();
		intent.putExtra("audioPath", Environment.getExternalStorageDirectory()+"/"+voiceName);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stop();
	}

	private void updateDisplay(double signalEMA) {

		switch ((int) signalEMA) {
		case 0:
		case 1:
			volume.setImageResource(R.drawable.amp7);
			break;
		case 2:
		case 3:
			volume.setImageResource(R.drawable.amp2);

			break;
		case 4:
		case 5:
			volume.setImageResource(R.drawable.amp3);
			break;
		case 6:
		case 7:
			volume.setImageResource(R.drawable.amp4);
			break;
		case 8:
		case 9:
			volume.setImageResource(R.drawable.amp5);
			break;
		case 10:
		case 11:
			volume.setImageResource(R.drawable.amp6);
			break;
		default:
			volume.setImageResource(R.drawable.amp7);
			break;
		}
	}
}
