package com.klowerbase.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView.BufferType;

import com.klower.interfaces.OnTextviewChangedListener;
import com.klower.textview.CollapsibleTextView;

public class TextViewActivity extends Activity implements
		OnTextviewChangedListener, OnClickListener {
	String s = "The following layout is an alternative to the layout shown in the previous lesson that shows only one fragment at a time. In order to replace one fragment with another, the activity's layout includes an empty FrameLayout that acts as the fragment container.";

	ImageView moreBtn;
	
	CollapsibleTextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textview_more);
		moreBtn = (ImageView) findViewById(R.id.more_btn);
		tv = (CollapsibleTextView) findViewById(R.id.desc_collapse_tv);
		tv.setMoreView(moreBtn);
		moreBtn.setOnClickListener(this);
		tv.setOnTextviewChangedListener(this);
		tv.setDesc(s, BufferType.NORMAL);
	}

//	@Override
//	public void showMore() {
//		moreBtn.setVisibility(View.VISIBLE);
//	}
//
//	@Override
//	public void hideMore() {
//		moreBtn.setVisibility(View.GONE);
//	}

	@Override
	public void reTract() {
		moreBtn.setImageResource(R.drawable.gk_image_triangle_up_review);
	}

	@Override
	public void spread() {
		moreBtn.setImageResource(R.drawable.gk_image_triangle_drop_review);
	}

	@Override
	public void onClick(View arg0) {
		tv.onMoreClick();
	}

}
