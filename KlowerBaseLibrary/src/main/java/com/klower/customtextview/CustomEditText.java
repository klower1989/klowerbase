/**
 * Copyright (c) 2012 Gainko.
 * All rights reserved
 */

package com.klower.customtextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends EditText {

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ComponentHelper.setFontFace(this, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        ComponentHelper.setFontFace(this, attrs);
    }

    public CustomEditText(Context context) {
        super(context);
    }

}
