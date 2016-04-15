package com.klowerbase.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.klower.swiplayout.RecyclerViewDragHolder;
import com.klower.titlebar.BaseException;
import com.klower.xrecycleview.XRecyclerView;
import com.klowerbase.test.adapter.MyViewHolder;

import java.util.ArrayList;

public class RecycleViewActivity extends ActionBarActivity {
    XRecyclerView recyclerView;
    MyAdapter myAdapter;
    @Override
    protected void onCreateEqually(Bundle savedInstanceState) throws BaseException {
        super.onCreateEqually(savedInstanceState);
        setCustomContentView(R.layout.ui_recycleview);

         recyclerView = (XRecyclerView) findViewById(R.id.recyclerView_vertical);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new  MyAdapter();

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


    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<String> items = new ArrayList<>();

        public MyAdapter() {
        }

        public void  setData(ArrayList<String> items){
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }

        public void  addData(ArrayList<String> items){
            this.items.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View deleteView = LayoutInflater.from(RecycleViewActivity.this).inflate(R.layout.ui_recycleview_item_delete, null);
            deleteView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            View contentView =  LayoutInflater.from(RecycleViewActivity.this).inflate(R.layout.ui_recycleview_item, null);
            contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            return new MyViewHolder(RecycleViewActivity.this, deleteView, contentView, RecyclerViewDragHolder.EDGE_RIGHT).getDragViewHolder();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            MyViewHolder myViewHolder = (MyViewHolder) RecyclerViewDragHolder.getHolder(holder);
            myViewHolder.content.setText(items.get(position));
            myViewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecycleViewActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecycleViewActivity.this, "delete position: "+position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }


    class MyViewHolder extends RecyclerViewDragHolder {

        TextView content, delete;

        public MyViewHolder(Context context, View bgView, View topView) {
            super(context, bgView, topView);
        }

        public MyViewHolder(Context context, View bgView, View topView, int mTrackingEdges) {
            super(context, bgView, topView, mTrackingEdges);
        }

        @Override
        public void initView(View itemView) {
            content = (TextView) itemView.findViewById(R.id.content);
            delete = (TextView) itemView.findViewById(R.id.delete_btn);
        }

    }
}
