package com.klowerbase.test;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.klower.adapter.GridViewPicAdapter;
import com.klower.component.CustomGridView;
import com.klower.slideexpandablelistView.ActionSlideExpandableListView;
import com.klower.slideexpandablelistView.ActionSlideExpandableListView.OnActionClickListener;
import com.klower.titlebar.BaseException;

public class SlideExpandableListViewActivity extends ActionBarActivity {

	ArrayList<String> urls = new ArrayList<String>();

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		// TODO Auto-generated method stub
		urls.add("http://e.hiphotos.baidu.com/image/pic/item/a2cc7cd98d1001e9c5dc5c08ba0e7bec55e797d1.jpg");
		urls.add("http://b.hiphotos.baidu.com/image/pic/item/f7246b600c338744222d657b530fd9f9d62aa0c9.jpg");
		urls.add("http://a.hiphotos.baidu.com/image/pic/item/5ab5c9ea15ce36d30decc93738f33a87e850b1c9.jpg");
		urls.add("http://d.hiphotos.baidu.com/image/pic/item/7aec54e736d12f2ebae6e6674dc2d562843568d1.jpg");
		urls.add("http://h.hiphotos.baidu.com/image/pic/item/b8389b504fc2d56282eb98eae51190ef77c66cd1.jpg");
		super.onCreateEqually(savedInstanceState);
		// set the content view for this activity, check the content view xml
		// file
		// to see how it refers to the ActionSlideExpandableListView view.
		setCustomContentView(R.layout.single_expandable_list);
		// get a reference to the listview, needed in order
		// to call setItemActionListener on it
		ActionSlideExpandableListView list = (ActionSlideExpandableListView) this
				.findViewById(R.id.list);

		// fill the list with data
		list.setAdapter(new ExpandAdapter());

		// listen for events in the two buttons for every list item.
		// the 'position' var will tell which list item is clicked
		list.setItemActionListener(new OnActionClickListener() {
			
			@Override
			public void onClick(View itemView, View clickedView, int position) {
				toast(clickedView.getTag().toString());
			}
		}, R.id.expandable_toggle_button);
	}

	/**
	 * Builds dummy data for the test. In a real app this would be an adapter
	 * for your data. For example a CursorAdapter
	 */
	// public ListAdapter buildDummyData() {
	// final int SIZE = 20;
	// String[] values = new String[SIZE];
	// for (int i = 0; i < SIZE; i++) {
	// values[i] = "Item " + i;
	// }
	// return new ArrayAdapter<String>(this, R.layout.expandable_list_item,
	// R.id.text, values);
	// }

	class ExpandAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(
						SlideExpandableListViewActivity.this).inflate(
						R.layout.expandable_list_item, null);
				holder.gridView = (CustomGridView) convertView
						.findViewById(R.id.gridview);
				holder.textView = (TextView) convertView.findViewById(R.id.category);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText("Category"+position);
			holder.gridView.setAdapter(new GridViewPicAdapter(
					SlideExpandableListViewActivity.this, urls, 5));
			holder.gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					holder.textView.setText("Category"+position+"  SubCategory: "+arg2);
					toast("position1: " + position);
					toast("position2: " + arg2);
				}
			});
			return convertView;
		}

		class ViewHolder {
			CustomGridView gridView;
			
			TextView textView;
		}

	}
}
