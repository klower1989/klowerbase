/**
 * Copyright (c) 2013 .
 * All rights reserved
 */

package com.klower.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate Dec 20, 2013
 */
public class CustomViewpager extends ViewPager {
	public CustomViewpager(Context context) {
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
	public CustomViewpager(Context context, AttributeSet attrs) {
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
