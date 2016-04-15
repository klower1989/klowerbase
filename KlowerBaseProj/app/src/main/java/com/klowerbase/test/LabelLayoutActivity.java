package com.klowerbase.test;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.klower.labellayout.LineBreakLayout;
import com.klower.titlebar.BaseException;
import com.klower.utilities.Utils;

public class LabelLayoutActivity extends ActionBarActivity {

	private Random random = new Random();

	private LineBreakLayout mBreakLayout;

	private int itemSize = 15;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.labellayout);
		mBreakLayout = (LineBreakLayout) findViewById(R.id.label_layout);
		addViewToLayout();
	}

	private void addViewToLayout() {
		TextView textView;
		LinearLayout.LayoutParams layoutParams;
		for (int i = 0; i < itemSize; i++) {
			textView = new TextView(this);
			textView.setPadding(20, 6, 20, 6);
			layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					Gravity.CENTER_VERTICAL);
			layoutParams.rightMargin = Utils.getDPValue(this, 50);
			textView.setText("Label" + i);
			textView.setBackgroundResource(R.drawable.grey_boder_bg);
			textView.setTextColor(randomColor());
			textView.setId(i);
			mBreakLayout.addView(textView, layoutParams);
		}

	}

	private int randomColor() {
		return randomColor(1);
	}

	private int randomColor(int rate) {
		int red = random.nextInt(256) / rate;
		int green = random.nextInt(256) / rate;
		int blue = random.nextInt(256) / rate;
		return Color.rgb(red, green, blue);
	}
}
