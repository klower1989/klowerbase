package com.klowerbase.test.helper;

import java.util.Date;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.klower.calendarview.CalendarCellDecorator;
import com.klower.calendarview.CalendarCellView;

public class SampleDecorator implements CalendarCellDecorator {
  @Override
  public void decorate(CalendarCellView cellView, Date date) {
    String dateString = Integer.toString(date.getDate());
    SpannableString string = new SpannableString(dateString + "\ntitle");
    string.setSpan(new RelativeSizeSpan(0.5f), 0, dateString.length(),
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    cellView.setText(string);
  }
}
