package com.klowerbase.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.klower.titlebar.BaseException;
import com.klower.utilities.LogTool;
import com.klower.wheelview.NumericWheelAdapter;
import com.klower.wheelview.OnWheelChangedListener;
import com.klower.wheelview.WheelView;

public class WheelViewActivity extends ActionBarActivity {

	WheelView year, month, day;
	String yearFormat = "%s年sdasdasdasdasda";
	String monthFormat = "%s月";
	String dayFormat = "%s日";
	int maxDay = 31;
	int yearValue = 2015, monthValue = 1, dayValue = 1;
	int textSize = 17;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.wheelview);
		year = (WheelView) findViewById(R.id.year);
		month = (WheelView) findViewById(R.id.month);
		day = (WheelView) findViewById(R.id.day);
		initCountryWheelView();

		year.setVisibleItems(5);
		year.setCyclic(true);
		NumericWheelAdapter yearAdapter = new NumericWheelAdapter(this, 2015,
				2100, yearFormat);
		yearAdapter.setTextSize(textSize);
		yearAdapter.setLabel("");
		year.setViewAdapter(yearAdapter);
		year.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				yearValue = 2015 + year.getCurrentItem();
				LogTool.d("year", "year : " + year.getCurrentItem());
			}
		});

		month.setVisibleItems(5);
		month.setCyclic(true);
		NumericWheelAdapter monthAdapter = new NumericWheelAdapter(this, 1, 12,
				monthFormat);
		monthAdapter.setTextSize(textSize);
		monthAdapter.setLabel("");
		month.setViewAdapter(monthAdapter);
		month.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				monthValue = 1 + month.getCurrentItem();
				LogTool.d("month", "month : " + month.getCurrentItem());
				updateDay(yearValue, monthValue);
			}
		});
		
		day.setVisibleItems(5);
		day.setCyclic(true);
		NumericWheelAdapter dayAdapter = new NumericWheelAdapter(this, 1, 31,
				dayFormat);
		dayAdapter.setTextSize(textSize);
		dayAdapter.setTextColor(Color.BLACK);
		dayAdapter.setLabel("");
		day.setViewAdapter(dayAdapter);
		day.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				LogTool.d("day", "day : " + day.getCurrentItem());
			}
		});

	}

	private void updateDay(int year, int month) {
		int max = 31;
		if (month == 2) {
			if ((year % 4 == 0 && year % 100 != 0)
					|| (year % 4 == 0 && year % 100 == 0)) {
				max = 29;
			} else {
				max = 28;
			}
		} else {
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				max = 31;
			}else{
				max = 30;
			}
		}
		NumericWheelAdapter dayAdapter = new NumericWheelAdapter(this, 1, max,
				dayFormat);
		dayAdapter.setTextSize(textSize);
		dayAdapter.setLabel("");
		dayAdapter.setTextColor(Color.BLACK);
		day.setViewAdapter(dayAdapter);
		day.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				LogTool.d("day", "day : " + day.getCurrentItem());
			}
		});
	}
	
	
	private void initCountryWheelView(){
		final WheelView country = (WheelView) findViewById(R.id.country);
		country.setWheelBackground(R.drawable.wheel_bg_holo);
		country.setWheelForeground(R.drawable.wheel_val_holo);
		country.setShadowColor(0x00000000, 0x00000000, 0x00000000);
		country.setVisibleItems(5);
		country.setViewAdapter(new CountryAdapter(this));
	}
	
	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends com.klower.wheelview.AbstractWheelTextAdapter {
		// Countries names
		private String countries[] =
				new String[] {"USA", "Canada", "Ukraine", "France"};
		// Countries flags
		private int flags[] =
				new int[] {R.drawable.usa, R.drawable.canada, R.drawable.ukraine, R.drawable.france};

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.country_layout, NO_RESOURCE);

			setItemTextResource(R.id.country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			ImageView img = (ImageView) view.findViewById(R.id.flag);
			img.setImageResource(flags[index]);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}
}
