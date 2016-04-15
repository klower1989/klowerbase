package com.klowerbase.test;

import android.graphics.Color;
import android.os.Bundle;

import com.klower.stepview.StepsView;
import com.klower.titlebar.BaseException;

public class StepViewActivity extends ActionBarActivity {

	StepsView mStepsView;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.stepview);

		mStepsView = (StepsView) findViewById(R.id.stepview);
		mStepsView
				.setLabels(
						new String[] { "Step1", "Step2", "Step3", "Step4",
								"Step5" }).setColorIndicator(Color.GREEN)
				.setBarColor(Color.RED).setLabelColor(Color.BLUE)
				.setCompletedPosition(3);
	}
}
