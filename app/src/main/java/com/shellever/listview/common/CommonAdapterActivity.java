package com.shellever.listview.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shellever.listview.R;

import java.util.ArrayList;
import java.util.List;

//
//1. ListView中Item控件抢占焦点问题解决：
//  <1.在抢占焦点的控件中设置其失去焦点
//      android:focusable="false"
//  <2.在抢占焦点的控件父布局中设置其焦点不下传
//      android:descendantFocusability="blocksDescendants"
//
//2. ListView复用导致内容错乱问题解决：
//
//
//

// Links:
// http://blog.csdn.net/lmj623565791/article/details/38902805/

public class CommonAdapterActivity extends AppCompatActivity {

    private ListView mCommonAdapterLv;
    private List<Bean> mBeanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_adapter);

        initDataList();
        initView();
    }

    private void initView() {
        mCommonAdapterLv = (ListView) findViewById(R.id.lv_common_adapter);
        CustomCommonAdapter adapter = new CustomCommonAdapter(this, mBeanList, R.layout.common_adapter_list_item);
        mCommonAdapterLv.setAdapter(adapter);
    }

    private void initDataList() {
        String title = "Android新技能 Get ";
        String desc = "Android打造万能ListView和GridView适配器";
        String time = "06/06/2016";
        String phone = "10086";
        for (int i = 1; i < 12; i++) {
            mBeanList.add(new Bean(title + i, desc, time, phone));
        }
    }
}
