package com.klowerbase.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.klower.component.CustomGridView;
import com.klower.titlebar.BaseException;
import com.klower.xlistview.XListView;
import com.klower.xlistview.XListView.IXListViewListener;

public class ListGridViewActivity extends ActionBarActivity implements IXListViewListener {

	private XListView mListView;
	ArrayAdapter<String> adapter1;
	
	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		// TODO Auto-generated method stub
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.xlistview);
		
		mListView = (XListView) findViewById(R.id.xlistview);
		View hearview = LayoutInflater.from(this).inflate(R.layout.testlistgridview, null);
		mListView.addHeaderView(hearview);
		
		CustomGridView gridView = (CustomGridView) hearview.findViewById(R.id.category_item_gridview);
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1);
		for (int i = 0; i < 20; i++) {
			adapter1.add("Text"+i);
		}
		gridView.setAdapter(adapter1);
		
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1);
//		adapter.add("Text"+0);
//		adapter.add("Text"+0);
//		adapter.add("Text"+0);
//		adapter.add("Text"+0);
		mListView.setAdapter(adapter);
		mListView.setDivider(null);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				toast("position: "+position);
			}
		});
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		for (int i = 10; i < 20; i++) {
			adapter1.add("Text"+i);
		}
		adapter1.notifyDataSetChanged();
	}
}
