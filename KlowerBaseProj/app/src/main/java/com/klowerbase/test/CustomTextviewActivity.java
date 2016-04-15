package com.klowerbase.test;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.klower.titlebar.BaseActivity;
import com.klower.titlebar.BaseException;
import com.klower.utilities.SpannableStringBuilderUtils;

public class CustomTextviewActivity extends BaseActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		getTitleBar().setCustomTitle("Welcome");
		getTitleBar().setRightTitle("右标题");
		setCustomContentView(R.layout.customtextview);

		TextView textView = (TextView) findViewById(R.id.stringbuilder);
		SpannableStringBuilderUtils spannableStringBuilderUtils = new SpannableStringBuilderUtils(
				"Hello World");
		spannableStringBuilderUtils.setColor(Color.RED, 0, 6);
		spannableStringBuilderUtils.setBold(Typeface.BOLD_ITALIC, 0, 1);
		spannableStringBuilderUtils.setDelLine(6, "Hello World".length());
		spannableStringBuilderUtils.setImage(
				getResources().getDrawable(R.drawable.composer_music), 5, 6);
		spannableStringBuilderUtils.setUnderLine(0, "Hello World".length());

		spannableStringBuilderUtils.append(new SpannableStringBuilderUtils(
				" Android").setColor(Color.GREEN, 0, " Android".length()));
		spannableStringBuilderUtils.setBackgroundColor(Color.GRAY, 0,
				spannableStringBuilderUtils.getSpannableStringBuilder()
						.length());
		spannableStringBuilderUtils.append(new SpannableStringBuilderUtils(
				"https://www.baidu.com/").setLink("https://www.baidu.com/", 0,
				"https://www.baidu.com/".length()));
		textView.setText(spannableStringBuilderUtils
				.getSpannableStringBuilder());
		//响应超链接
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		textView.setAutoLinkMask(Linkify.ALL);
	}

	@Override
	public void onRightTitleClick() {
		super.onRightTitleClick();
	}

	@Override
	public void onLeftTitleClick() {
		super.onLeftTitleClick();
	}
}
