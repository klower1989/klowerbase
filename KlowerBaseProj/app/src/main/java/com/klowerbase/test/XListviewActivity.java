package com.klowerbase.test;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import com.klower.emptylayout.EmptyLayout;
import com.klower.titlebar.BaseException;
import com.klower.xlistview.XListView;
import com.klower.xlistview.XListView.IXListViewListener;

public class XListviewActivity extends ActionBarActivity implements
		IXListViewListener, OnClickListener {

	private XListView mListView;
	
	private EmptyLayout mEmptyLayout;
	
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.xlistview);

		mListView = (XListView) findViewById(R.id.xlistview);
		mEmptyLayout = new EmptyLayout(this, mListView);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		
		mEmptyLayout.setShowEmptyButton(true);
		mEmptyLayout.setEmptyButtonClickListener(this);
		mEmptyLayout.setShowErrorButton(true);
		mEmptyLayout.setErrorButtonClickListener(this);
		
		mEmptyLayout.setEmptyMessage("未搜索到结果！");
		mEmptyLayout.setErrorMessage("网络连接异常！");
		mEmptyLayout.setLoadingMessage("数据加载中...");
		mEmptyLayout.setRetryButtonMsg("重试");
		mEmptyLayout.showEmpty();
		
		
	}

	@Override
	public void onRefresh() {
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1);
		for (int i = 0; i < 10; i++) {
			adapter.add("Text"+i);
		}
		mListView.setAdapter(adapter);
		
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setPullLoadEnable(true);
	}

	@Override
	public void onLoadMore() {

		adapter.clear();
		adapter.notifyDataSetChanged();
		
		
		mEmptyLayout.showError();
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == mEmptyLayout.getEmptyViewButtonId()){
			mListView.startRefresh();
		}else if(v.getId() == mEmptyLayout.getErrorViewButtonId()){
			mListView.startRefresh();
		}
	}
}
