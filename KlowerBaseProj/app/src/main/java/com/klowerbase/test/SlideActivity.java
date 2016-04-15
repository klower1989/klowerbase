package com.klowerbase.test;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.klower.titlebar.BaseException;

public class SlideActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";

	 private ListView mListView;
	    private Context mContext = this;


@Override
protected void onCreateEqually(Bundle savedInstanceState)
		throws BaseException {
	// TODO Auto-generated method stub
	super.onCreateEqually(savedInstanceState);
	setCustomContentView(R.layout.slide);
//	initView();
}


//	private void initView() {
//
//        /**
//         * The following comment is the sample usage of ArraySwipeAdapter.
//         */
////        String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
////                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
////                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
////                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};
////        mListView.setAdapter(new ArraySwipeAdapterSample<String>(this, R.layout.listview_item, R.id.position, adapterData));
//		mListView = (ListView) findViewById(R.id.list);
//        mAdapter = new ListViewAdapter(this);
//        mListView.setAdapter(mAdapter);
//        mAdapter.setMode(Attributes.Mode.Single);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            	if(mAdapter.isOpen(position)){
//
//            	}else{
//            		toast("position: "+position);
//            	}
////                ((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
//            }
//        });
//        mListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("ListView", "OnTouch");
//                return false;
//            }
//        });
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.e("ListView", "onScrollStateChanged");
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
//
//        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("ListView", "onItemSelected:" + position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("ListView", "onNothingSelected:");
//            }
//        });
//	}

	
}
