package com.klowerbase.test;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.klower.titlebar.BaseException;
import com.klower.xrecycleview.XRecyclerView;
import com.klowerbase.test.adapter.RecycleviewStagGridAdapter;

import java.util.ArrayList;

/**
 * Created by shichaohui on 2015/7/31 0031.
 */
public class RecycleViewTest extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    XRecyclerView recyclerView;
    RecycleviewStagGridAdapter myAdapter;
    @Override
    protected void onCreateEqually(Bundle savedInstanceState) throws BaseException {
        super.onCreateEqually(savedInstanceState);
        setCustomContentView(R.layout.ui_recycleview);

        recyclerView = (XRecyclerView) findViewById(R.id.recyclerView_vertical);
        final StaggeredGridLayoutManager manager4 = (new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(manager4);
        myAdapter = new RecycleviewStagGridAdapter(this, this, this);

        ArrayList<String> items = new ArrayList<>();
        items.add("dadadasdasdasdasda");
        items.add("dadadasdasdasdasda");
        items.add("dadadasdasdasdasda");
        items.add("dadadasdasdasdasda");
        items.add("dasdadasddddddddddddd");
        items.add("dadadasdasdasdasda");
        items.add("dadadasdasdasdasda");
        items.add("dadadasdasdasdasda");
        recyclerView.setAdapter(myAdapter);
        myAdapter.setData(items);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                ArrayList<String> items = new ArrayList<>();
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dasdadasddddddddddddd");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                myAdapter.setData(items);
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete();
                if(myAdapter.getItemCount() > 35){
                    return;
                }
                ArrayList<String> items = new ArrayList<>();
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dasdadasddddddddddddd");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                items.add("dadadasdasdasdasda");
                myAdapter.addData(items);

            }
        });

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
