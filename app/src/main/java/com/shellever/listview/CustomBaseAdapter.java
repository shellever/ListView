package com.shellever.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Author: Shellever
 * Date:   11/12/2016
 * Email:  shellever@163.com
 */
// ListView优化：通过convertView+ViewHolder来实现

// ViewHolder优化BaseAdapter思路：
//        创建Bean对象，用于封装数据
//        在构造方法中初始化用于映射的数据List
//        创建ViewHolder类，创建布局映射关系
//        判断convertView，为空则创建，并设置tag，否则通过tag来取出ViewHolder
//        给ViewHolder中的控件设置数据

public class CustomBaseAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<UserBean> mUserBeanList;

    public CustomBaseAdapter(Context context, List<UserBean> userBeanList) {
        mUserBeanList = userBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mUserBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    // 1. 利用了ListView的缓存特性，若没有缓存才创建新的View，避免了重复创建大量的convertView
    // 2. 通过ViewHolder类来实现显示数据的视图的缓存，避免多次通过findViewById寻找控件
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {  // 如果缓存convertView为空，则需要创建View
            vh = new ViewHolder();

            // 根据上下文环境实例化一个布局文件base_adapter_list_item.xml
            convertView = inflater.inflate(R.layout.base_adapter_list_item, null);

            vh.iv_icon = (ImageView) convertView.findViewById(R.id.iv_user_icon);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            vh.tv_desc = (TextView) convertView.findViewById(R.id.tv_user_desc);

            // 将ViewHolder关联到convertView中，避免下次从findViewById中查找控件
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        UserBean user = mUserBeanList.get(position);
        vh.iv_icon.setImageResource(user.getIconResId());
        vh.tv_name.setText(user.getName());
        vh.tv_desc.setText(user.getDescription());

        return convertView;
    }

    // 创建静态内部类ViewHolder，用于缓存ListView中Item布局的每个控件
    // 一个ViewHolder对应ListView中的一个Item布局对象
    private static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }
}
