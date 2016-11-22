package com.shellever.listview.common;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.shellever.listview.R;
import com.shellever.listview.common.utils.CommonAdapter;
import com.shellever.listview.common.utils.ViewHolder;

import java.util.List;

/**
 * Author: Shellever
 * Date:   11/12/2016
 * Email:  shellever@163.com
 */

public class CustomCommonAdapter extends CommonAdapter<Bean> {

    public CustomCommonAdapter(Context context, List<Bean> dataList, int layoutId) {
        super(context, dataList, layoutId);     // 必须调用父类构造器来初始化父类
    }

    @Override
    public void convert(ViewHolder holder, final Bean bean) { // 此方法由父类进行回调
        holder.setText(R.id.id_title, bean.getTitle())
                .setText(R.id.id_desc, bean.getDesc())
                .setText(R.id.id_time, bean.getTime())
                .setText(R.id.id_phone, bean.getPhone());

        final CheckBox cb = (CheckBox) holder.getView(R.id.id_check);
        cb.setChecked(bean.isChecked());        // 用于解决CheckBox复用上出现的重复问题
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setChecked(cb.isChecked());
            }
        });
    }
}
