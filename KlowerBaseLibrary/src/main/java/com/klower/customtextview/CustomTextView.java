/**
 * All rights reserved
 */

package com.klower.customtextview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

	
	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 ComponentHelper.setFontFace(this, attrs);
	}

	public CustomTextView(Context context) {
		super(context);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 ComponentHelper.setFontFace(this, attrs);
	}
	
	public void setCustomFontStyle(String fontName){
	    ComponentHelper.setCustomFontStyle(this, fontName);
	}

}
