package com.klower.togglebar;

import com.klower.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomToggleBar extends LinearLayout {

	private String[] tabName = new String[] { "Tab1", "Tab2", "Tab3", "Tab4" };

	private int tabCount = 2;

	private int defaultTabBg = Color.GREEN;

	private int unSelectedTabBg = Color.WHITE;

	private int cornesRadius = 10;

	private int textSize = 18;

	private TextView[] bars;

	private OnBarClicklistener barClicklistener;

	private GradientDrawable bgDrawable, leftDefaultDrawable,
			centerDefaultDrawable, rightDefaultDrawable;
	
	private int disableSelectIndex = -1;
	
	private int direction = 0;

	public CustomToggleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomToggleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
	}

	public CustomToggleBar(Context context) {
		super(context);
	}

	public void setOnBarClicklistener(OnBarClicklistener clicklistener) {
		barClicklistener = clicklistener;
	}
	

	public int getDisableSelectIndex() {
		return disableSelectIndex;
	}

	public void setDisableSelectIndex(int disableSelectIndex) {
		this.disableSelectIndex = disableSelectIndex;
	}

	private void initViews(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.togglebar);
		String barString = a.getString(R.styleable.togglebar_barString);
		textSize = (int) a.getDimensionPixelSize(R.styleable.togglebar_barTextSize, textSize);
		tabName = barString.split(",");
		defaultTabBg = a.getColor(R.styleable.togglebar_barSelectColor,
				defaultTabBg);
		unSelectedTabBg = a.getColor(R.styleable.togglebar_barDefaultColor,
				unSelectedTabBg);
		direction = a.getInteger(R.styleable.togglebar_direction,
				direction);
		initBg();
		setBackgroundDrawable(bgDrawable);
		tabCount = tabName.length;
		bars = new TextView[tabCount];
		if(direction == 0){
			setOrientation(HORIZONTAL);
		}else{
			setOrientation(VERTICAL);
		}

		TextView textView;
		LayoutParams params;
		for (int i = 0; i < tabCount; i++) {
			textView = new TextView(context);
			params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 1);
			textView.setId(i);
			textView.setText(tabName[i]);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			textView.setGravity(Gravity.CENTER);
			if (i == 0) {
				setTag("Tab" + i);
				params.rightMargin = 1;
				params.leftMargin = 1;
				params.topMargin = 1;
				params.bottomMargin = 1;
				textView.setTextColor(unSelectedTabBg);
			} else if (i == tabCount - 1) {
				
				if(direction == 0){
					params.rightMargin = 1;
					params.topMargin = 1;
					params.bottomMargin = 1;
				}else{
					params.rightMargin = 1;
					params.leftMargin = 1;
					params.bottomMargin = 1;
				}
				textView.setTextColor(defaultTabBg);
				textView.setBackgroundDrawable(rightDefaultDrawable);
			} else {
				if(direction ==0){
					params.rightMargin = 1;
					params.topMargin = 1;
					params.bottomMargin = 1;
				}else{
					params.leftMargin = 1;
					params.rightMargin = 1;
					params.bottomMargin = 1;
				}
				
				textView.setTextColor(defaultTabBg);
				textView.setBackgroundDrawable(centerDefaultDrawable);
			}
			bars[i] = textView;
			textView.setOnClickListener(new ToggleBarCliclistener());
			addView(textView, i, params);
		}
		
		a.recycle();
	}

	class ToggleBarCliclistener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (("Tab" + v.getId()).equalsIgnoreCase(getTag().toString())) {
				if(barClicklistener != null){
					barClicklistener.onBarRepeatClick(v.getId());
				}
				return;
			} else {
				if(barClicklistener != null){
					barClicklistener.onBarClick(v.getId());
				}
				if(disableSelectIndex == v.getId()){
				}else{
					setTag("Tab" + v.getId());
					onBarStatusChange(v.getId());
				}
				
			}

		}

	}
	
	public void setTabSelected(int position){
		View v = bars[position];
		if (("Tab" + v.getId()).equalsIgnoreCase(getTag().toString())) {
			if(barClicklistener != null){
				barClicklistener.onBarRepeatClick(v.getId());
			}
			return;
		} else {
			if(barClicklistener != null){
				barClicklistener.onBarClick(v.getId());
			}
			if(disableSelectIndex == v.getId()){
			}else{
				setTag("Tab" + v.getId());
				onBarStatusChange(v.getId());
			}
			
		}

	
	}

	private void onBarStatusChange(int selectedIndex) {
		for (int i = 0; i < bars.length; i++) {
			if (selectedIndex == i) {
				bars[i].setTextColor(unSelectedTabBg);
				bars[i].setBackgroundDrawable(null);
			} else {
				if (i == 0) {
					bars[i].setTextColor(defaultTabBg);
					bars[i].setBackgroundDrawable(leftDefaultDrawable);
				} else if (i == tabCount - 1) {
					bars[i].setTextColor(defaultTabBg);
					bars[i].setBackgroundDrawable(rightDefaultDrawable);
				} else {
					bars[i].setTextColor(defaultTabBg);
					bars[i].setBackgroundDrawable(centerDefaultDrawable);
				}

			}
		}
	}

	private void initBg() {
		bgDrawable = new GradientDrawable();
		bgDrawable.setColor(defaultTabBg);
		bgDrawable.setCornerRadius(cornesRadius);

		leftDefaultDrawable = new GradientDrawable();
		leftDefaultDrawable.setColor(unSelectedTabBg);
		if(direction == 0){
			leftDefaultDrawable.setCornerRadii(new float[] { cornesRadius,
					cornesRadius, 0, 0, 0, 0, cornesRadius, cornesRadius });
		}else{
			//1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
			leftDefaultDrawable.setCornerRadii(new float[] { cornesRadius,
					cornesRadius, cornesRadius, cornesRadius,
					0, 0, 0,
					0 });
		}
		

		rightDefaultDrawable = new GradientDrawable();
		rightDefaultDrawable.setColor(unSelectedTabBg);
		if(direction == 0){
			rightDefaultDrawable.setCornerRadii(new float[] { 0, 0, cornesRadius,
					cornesRadius, cornesRadius, cornesRadius, 0, 0 });
		}else{
			rightDefaultDrawable.setCornerRadii(new float[] { 0, 0, 0,
					0, cornesRadius, cornesRadius, cornesRadius, cornesRadius });
		}
		

		centerDefaultDrawable = new GradientDrawable();
		centerDefaultDrawable.setColor(unSelectedTabBg);
		centerDefaultDrawable.setCornerRadii(new float[] { 0, 0, 0, 0, 0, 0, 0,
				0 });
	}

	public interface OnBarClicklistener {

		public void onBarClick(int position);
		
		public void onBarRepeatClick(int position);
	}
}
