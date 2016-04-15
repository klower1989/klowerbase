package com.klower.utilities;

import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

public class SpannableStringBuilderUtils {

	String str;

	SpannableStringBuilder mStringBuilder;

	public SpannableStringBuilderUtils(String str) {
		super();
		this.str = str;
		mStringBuilder = new SpannableStringBuilder(str);
	}

	public SpannableStringBuilder setColor(int color, int start, int end) {
		mStringBuilder.setSpan(new ForegroundColorSpan(color), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mStringBuilder;
	}
	
	public SpannableStringBuilder setBackgroundColor(int color, int start, int end) {
		mStringBuilder.setSpan(new BackgroundColorSpan(color), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mStringBuilder;
	}

	public SpannableStringBuilder setLink(String link, int start, int end){
		mStringBuilder.setSpan(new URLSpan(link), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mStringBuilder;
	}

	/**
	 * 
	 * @param typeface: Typeface.BOLD, Typeface.BOLD_ITALIC, Typeface.ITALIC, 
	 * @param start
	 * @param end
	 * @return
	 */
	public SpannableStringBuilder setBold(int typeface, int start, int end){
		mStringBuilder.setSpan(new StyleSpan(typeface), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mStringBuilder;
	}
	
	public SpannableStringBuilder setDelLine(int start, int end){
		mStringBuilder.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mStringBuilder;
	}
	
	public SpannableStringBuilder setUnderLine(int start, int end){
		mStringBuilder.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mStringBuilder;
	}
	
	
	public SpannableStringBuilder setImage(Drawable drawable, int start, int end){
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
		mStringBuilder.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mStringBuilder;
	}
	
	
	public SpannableStringBuilder append(SpannableStringBuilder stringBuilder){
		return mStringBuilder.append(stringBuilder);
	} 
	
	public SpannableStringBuilder getSpannableStringBuilder(){
		return mStringBuilder;
	}
	
	
	
	
	
}
