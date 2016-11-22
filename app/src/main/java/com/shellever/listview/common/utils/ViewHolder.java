package com.shellever.listview.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author: Shellever
 * Date:   11/12/2016
 * Email:  shellever@163.com
 */

public class ViewHolder {

    private SparseArray<View> mViewBuffer;       // mViewBuffer
    private View mConvertView;


    private ViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.mViewBuffer = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);              // View.setTag(ViewHolder);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public View getView(int viewId) {
        View view = mViewBuffer.get(viewId);    // Firstly, get View from ViewBuffer by viewId
        if (view == null) {                     // Then, use View.findViewById(viewId) if null
            view = mConvertView.findViewById(viewId);
            mViewBuffer.put(viewId, view);      // Last, store the view into ViewBuffer
        }
        return view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    //
    // setter() - supports chain programming
    //
    public ViewHolder setText(int viewId, String text) {
        TextView view = (TextView) getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = (ImageView) getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = (ImageView) getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageURI(int viewId, Uri uri) {
        ImageView view = (ImageView) getView(viewId);
        view.setImageURI(uri);
        return this;
    }
}