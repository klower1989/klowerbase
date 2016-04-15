package com.klower.interfaces;

import android.content.Intent;
import android.graphics.Bitmap;

import com.google.zxing.Result;

public interface OnTwodimensioncodeListener {

	void handleDecode(Result obj, Bitmap barcode);
	
	void setCodeResult(int resultCode, Intent intent);
}
