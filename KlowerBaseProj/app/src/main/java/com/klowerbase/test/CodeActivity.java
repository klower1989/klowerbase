package com.klowerbase.test;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.klower.code.Code;
import com.klower.titlebar.BaseException;

public class CodeActivity extends ActionBarActivity {

	ImageView mCodeImg;
	Code mCode;
	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.code);
		
		mCode = new Code(this);
		mCodeImg = (ImageView) findViewById(R.id.code);
		mCodeImg.setImageBitmap(mCode.getBitmap());
		mCodeImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toast("code: "+mCode.getCode());
				mCodeImg.setImageBitmap(mCode.getBitmap());
			}
		});
	}
}
