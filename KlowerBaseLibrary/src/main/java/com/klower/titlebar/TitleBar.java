package com.klower.titlebar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.klower.R;
import com.klower.interfaces.OnActionbarListener;
import com.klower.utilities.Utils;

public class TitleBar extends LinearLayout {

	OnActionbarListener mActionbarListener;
	
	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mActionbarListener =  (OnActionbarListener) context;
		initView(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mActionbarListener =  (OnActionbarListener) context;
		initView(context);
	}

	public TitleBar(Context context) {
		super(context);
		mActionbarListener =  (OnActionbarListener) context;
		initView(context);
	}

	private void initView(Context context) {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, Utils.getDPValue(context, 45));
		addView(LayoutInflater.from(context).inflate(R.layout.ui_common_title,
				null), params);
		findViewById(R.id.title_left_icon).setOnClickListener(
				new OnTitleClickListener());
		findViewById(R.id.title_left_txt).setOnClickListener(
				new OnTitleClickListener());
		findViewById(R.id.title_right_icon).setOnClickListener(
				new OnTitleClickListener());
		findViewById(R.id.title_right_txt).setOnClickListener(
				new OnTitleClickListener());
		findViewById(R.id.title_right_second_txt).setOnClickListener(
				new OnTitleClickListener());
	}

	class OnTitleClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.title_left_txt
					|| v.getId() == R.id.title_left_icon) {
				if(mActionbarListener != null){
					mActionbarListener.onLeftTitleClick();
				}
			} else if (v.getId() == R.id.title_right_txt
					|| v.getId() == R.id.title_right_icon) {
				if(mActionbarListener != null){
					mActionbarListener.onRightTitleClick();
				}
			}else if(v.getId() == R.id.title_right_second_txt){
				if(mActionbarListener != null){
					mActionbarListener.onRightSecondTitleClick();
				}

			}
		}

	}



	public void setCustomTitle(String txt) {
		TextView title = (TextView) findViewById(R.id.title_title);
		title.setText(txt);
	}
	
	public void setCustomTitleColor(int color) {
		TextView title = (TextView) findViewById(R.id.title_title);
		title.setTextColor(color);
	}

	public void setCustomTitle(int id) {
		String txt = getContext().getString(id);
		setCustomTitle(txt);
	}

	public void setLeftTitle(String txt) {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_left_icon);
		leftIcon.setVisibility(View.INVISIBLE);
		TextView leftTitle = (TextView) findViewById(R.id.title_left_txt);
		leftTitle.setText(txt);
		leftTitle.setVisibility(View.VISIBLE);
	}
	public void hideLeftTitle() {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_left_icon);
		leftIcon.setVisibility(View.INVISIBLE);
		TextView leftTitle = (TextView) findViewById(R.id.title_left_txt);
		leftTitle.setVisibility(View.INVISIBLE);
	}


	public void setLeftTitle(String txt,int leftIconVisible) {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_left_icon);
		leftIcon.setVisibility(leftIconVisible);
		TextView leftTitle = (TextView) findViewById(R.id.title_left_txt);
		leftTitle.setText(txt);
		leftTitle.setVisibility(View.VISIBLE);
	}

	public void setLeftTitle(int id) {
		String txt = getContext().getString(id);
		setLeftTitle(txt);
	}

	public void setRightTitle(String txt) {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_right_icon);
		leftIcon.setVisibility(View.INVISIBLE);
		TextView rightTitle = (TextView) findViewById(R.id.title_right_txt);
		rightTitle.setText(txt);
		rightTitle.setVisibility(View.VISIBLE);
	}

	public void setRightTitle(String txt, Drawable background) {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_right_icon);
		leftIcon.setVisibility(View.INVISIBLE);
		TextView rightTitle = (TextView) findViewById(R.id.title_right_txt);
		rightTitle.setText(txt);
		rightTitle.setVisibility(View.VISIBLE);
		rightTitle.setBackgroundDrawable(background);
	}

	public void setRightSecondTitle(String txt, Drawable background) {
		TextView rightTitle = (TextView) findViewById(R.id.title_right_second_txt);
		rightTitle.setText(txt);
		rightTitle.setVisibility(View.VISIBLE);
		rightTitle.setBackgroundDrawable(background);
	}

	public void setRightSecondTitle(String txt, int background) {
		TextView rightTitle = (TextView) findViewById(R.id.title_right_second_txt);
		rightTitle.setText(txt);
		rightTitle.setVisibility(View.VISIBLE);
		rightTitle.setBackgroundResource(background);
	}

	public void setRightTitle(int id) {
		String txt = getContext().getString(id);
		setRightTitle(txt);
	}

	public void setLeftTitleIcon(int id) {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_left_icon);
		leftIcon.setImageResource(id);
		leftIcon.setVisibility(View.VISIBLE);
		TextView leftTitle = (TextView) findViewById(R.id.title_left_txt);
		leftTitle.setVisibility(View.INVISIBLE);
	}

	public void setLeftTitleIconTitle(int id, String title) {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_left_icon);
		leftIcon.setImageResource(id);
		leftIcon.setVisibility(View.VISIBLE);
		TextView leftTitle = (TextView) findViewById(R.id.title_left_txt);
		leftTitle.setVisibility(View.VISIBLE);
		leftTitle.setText(title);
	}

	public void setRightTitleIcon(int id) {
		ImageView rightIcon = (ImageView) findViewById(R.id.title_right_icon);
		rightIcon.setImageResource(id);
		rightIcon.setVisibility(View.VISIBLE);
		TextView rightTitle = (TextView) findViewById(R.id.title_right_txt);
		rightTitle.setVisibility(View.INVISIBLE);
	}

	public void setRightSecondTitleIcon(int id) {
		ImageView rightIcon = (ImageView) findViewById(R.id.title_right_second_icon);
		rightIcon.setImageResource(id);
		rightIcon.setVisibility(View.VISIBLE);
	}

	public void setRightTitle(int bg, int color) {
		TextView rightTitle = (TextView) findViewById(R.id.title_right_txt);
		rightTitle.setBackgroundResource(bg);
		rightTitle.setTextColor(color);
	}

	public void setTitleCommonColor(int color){
		((TextView) findViewById(R.id.title_left_txt)).setTextColor(color);
		((TextView) findViewById(R.id.title_title)).setTextColor(color);
		((TextView) findViewById(R.id.title_right_txt)).setTextColor(color);

	}

	public View getRightView() {
		ImageView rightIcon = (ImageView) findViewById(R.id.title_right_icon);
		return rightIcon;
	}

	public void invisibleTitle() {
//		findViewById(R.id.ui_common_title).setVisibility(View.GONE);
	}

	public void hideLeftTitleArrow() {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_left_icon);
		leftIcon.setVisibility(View.INVISIBLE);
	}

	public void hideRightTitle() {
		ImageView leftIcon = (ImageView) findViewById(R.id.title_right_icon);
		leftIcon.setVisibility(View.INVISIBLE);
		TextView leftTitle = (TextView) findViewById(R.id.title_right_txt);
		leftTitle.setVisibility(View.INVISIBLE);
	}
	
	public View getTitleLine(){
		return findViewById(R.id.title_line);
	}

	public TextView getLeftTitleView(){
		return (TextView) findViewById(R.id.title_left_txt);
	}

	public ImageView getLeftTitleIcon(){
		return (ImageView) findViewById(R.id.title_left_icon);
	}

	public TextView getRightTitleView(){
		return (TextView) findViewById(R.id.title_right_txt);
	}
}
