package com.klowerbase.test;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.klower.keyboard.KeyboardUtil;
import com.klower.titlebar.BaseException;

public class KeyBoardActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		// TODO Auto-generated method stub
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.keyboard);
		
		final EditText input = (EditText) findViewById(R.id.input);
		input.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int inputback = input.getInputType();
				input.setInputType(InputType.TYPE_NULL);
				KeyboardUtil kb = new KeyboardUtil(KeyBoardActivity.this,input);
				kb.showKeyboard();
				input.setInputType(inputback);
				v.performClick();
				return false;
			}
		});

	}
}
