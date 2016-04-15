package com.klowerbase.test;

import android.os.Bundle;
import android.view.View;

import com.klower.rubberview.RubberView;
import com.klower.titlebar.BaseException;

public class RubberViewActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.rubberview);
		final RubberView rubberView = (RubberView) findViewById(R.id.rubberView_main);
		rubberView.enableAcrossMonitor(findViewById(R.id.text),
				new RubberView.OnAcrossHintViewListener() {
					private boolean across;

					@Override
					public void onAcrossHintView(View hintView) {
						if (!across) {
							across = true;
							rubberView
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											finish();
										}
									});
						}
					}
				});

	}
}
