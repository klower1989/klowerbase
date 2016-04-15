/**
 * Copyright (c) 2013 .
 * All rights reserved
 */

package com.klower.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate Dec 20, 2013
 */
public class CustomScrollView2 extends ScrollView {
	public CustomScrollView2(Context context) {
		super(context);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attribute set
	 */
	public CustomScrollView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
				height = h;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
				MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
