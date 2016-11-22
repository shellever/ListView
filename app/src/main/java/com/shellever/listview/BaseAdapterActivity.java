package com.shellever.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);

        ListView mBaseAdapterLv = (ListView) findViewById(R.id.lv_base_adapter);

        List<UserBean> mUserBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mUserBeanList.add(new UserBean(R.mipmap.ic_launcher, "Launcher - " + i, "I am Launcher - " + i));
        }

        CustomBaseAdapter adapter = new CustomBaseAdapter(this, mUserBeanList);
        mBaseAdapterLv.setAdapter(adapter);

        mBaseAdapterLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_desc = (TextView) view.findViewById(R.id.tv_user_desc);
                Toast.makeText(BaseAdapterActivity.this, tv_desc.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
