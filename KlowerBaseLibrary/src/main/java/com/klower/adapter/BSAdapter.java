package com.klower.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.klower.R;
import com.klower.utilities.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class BSAdapter<T> extends BaseAdapter {

    protected Context mContext;

    protected LayoutInflater mLayoutInflater;

    ArrayList<T> items = new ArrayList<T>();

    protected ImageLoader imageLoader;

    protected DisplayImageOptions options;

    public BSAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        imageLoader = ImageLoader.getInstance();
        options = Utils
                .getNormalDisplayImageOptions(R.drawable.lh_photo_default);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return null;
    }


    public void clearData() {
        items.clear();
        notifyDataSetChanged();
    }

    public void setData(ArrayList<T> list) {
        if (list != null) {
            items.clear();
            items.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addData(ArrayList<T> list) {
        if (list != null) {
            items.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addData(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public ArrayList<T> getData() {
        return items;
    }

    public T getEntityClass(int position) {
        return (T) items.get(position);

    }

}
