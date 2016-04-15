package com.klower.pin;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.klower.R;
import com.klower.component.ShapeTextView;
import com.klower.utilities.Utils;

public class CustomPinShowView extends LinearLayout {

	int pinNum = 4;

	TextView[] pins = new TextView[pinNum];

	GradientDrawable defaultDrawable, selectedDrawable;

	public CustomPinShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews(context);
	}

	public CustomPinShowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}

	public CustomPinShowView(Context context) {
		super(context);
		initViews(context);
	}

	private void initViews(Context context) {
		setGravity(Gravity.CENTER);
		setOrientation(HORIZONTAL);
		defaultDrawable = ShapeTextView.getBgDrawable(
				getResources().getColor(R.color.TextLightGray), 1,
				getResources().getColor(R.color.TextGray), 0,
				GradientDrawable.OVAL);
		selectedDrawable = ShapeTextView.getBgDrawable(
				getResources().getColor(R.color.green_color), 0, getResources()
						.getColor(R.color.TextGray), 0, GradientDrawable.OVAL);
		LayoutParams layoutParams = new LayoutParams(
				Utils.getDPValue(context, 30), Utils.getDPValue(context,30));
		TextView textView;
		for (int i = 0; i < pinNum; i++) {
			if(i != 0){
				layoutParams.leftMargin = Utils.getDPValue(context, 20);
			}
			textView = new TextView(context);
			textView.setBackgroundDrawable(defaultDrawable);
			pins[i] = textView;
			addViewInLayout(textView, i, layoutParams);
		}
	}

	public void changeStatus(int pinSize) {
		for (int i = 0; i < pinNum; i++) {
			if (i <= pinSize - 1) {
				pins[i].setBackgroundDrawable(selectedDrawable);
			}else{
				pins[i].setBackgroundDrawable(defaultDrawable);
			}

		}
	}

}
