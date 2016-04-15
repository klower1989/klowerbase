package com.klowerbase.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.klower.titlebar.BaseException;
import com.klowerbase.test.fragments.TestFragment2;

public class TabHostFragmentActivity extends ActionBarActivity implements OnTabChangeListener {
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;

	private Class<?> fragmentArray[] = { TestFragment.class,
			TestFragment2.class, TestFragment.class, TestFragment2.class };

	private int[] mImageViewArray = { R.drawable.tab_more_selector,
			R.drawable.tab_more_selector, R.drawable.tab_more_selector,
			R.drawable.tab_more_selector };

	private String[] mTextviewArray = { "验证", "评价", "结款", "更多" };

	private int mIndex = 0;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.ui_main_tab_layout);
		initView();
	}

	private void initView() {
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.TRANSPARENT);
			mTabHost.getTabWidget().getChildAt(i).setTag("" + i);
			// mTabHost.getTabWidget().setShowDividers(0);
			mTabHost.setOnTabChangedListener(this);
		}
	}

	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.ui_main_tab_item, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		return view;
	}

	@Override
	public void onTabChanged(String tabId) {
		
	}
}
