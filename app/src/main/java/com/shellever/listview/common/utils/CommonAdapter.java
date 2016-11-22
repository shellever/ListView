package com.shellever.listview.common.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * Author: Shellever
 * Date:   11/12/2016
 * Email:  shellever@163.com
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDataList;
    private int mLayoutId;

    public CommonAdapter(Context context, List<T> dataList, int layoutId){
        this.mContext = context;
        this.mDataList = dataList;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayoutId);

        convert(holder, getItem(position));  // binding

        return holder.getConvertView();
    }

    // data binding between view and bean by user
    public abstract void convert(ViewHolder holder, T t);
}
