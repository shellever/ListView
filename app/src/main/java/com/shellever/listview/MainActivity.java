package com.shellever.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.shellever.listview.common.CommonAdapterActivity;

// http://blog.csdn.net/tianshuguang/article/details/7344315

// 列表的适配器类型：ArrayAdapter，SimpleAdapter
// 其中以ArrayAdapter最为简单，只能展示一行字。
// SimpleAdapter有最好的扩充性，可以自定义出各种效果。
// http://www.cnblogs.com/allin/archive/2010/05/11/1732200.html
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mEntriesBtn;
    private Button mSimpleAdapterBtn;
    private Button mSingleArrayAdapterBtn;
    private Button mMultipleArrayAdapterBtn;
    private Button mBaseAdapterBtn;
    private Button mListActivityBtn;
    private Button mRefreshPagingBtn;
    private Button mCommonAdapterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEntriesBtn = (Button) findViewById(R.id.btn_entries);
        mSimpleAdapterBtn = (Button) findViewById(R.id.btn_simple_adapter);
        mSingleArrayAdapterBtn = (Button) findViewById(R.id.btn_array_adapter_single);
        mMultipleArrayAdapterBtn = (Button) findViewById(R.id.btn_array_adapter_multiple);
        mBaseAdapterBtn = (Button) findViewById(R.id.btn_base_adapter);
        mListActivityBtn = (Button) findViewById(R.id.btn_list_activity);
        mRefreshPagingBtn = (Button) findViewById(R.id.btn_refresh_paging);
        mCommonAdapterBtn = (Button) findViewById(R.id.btn_common_adapter);

        mEntriesBtn.setOnClickListener(this);
        mSimpleAdapterBtn.setOnClickListener(this);
        mSingleArrayAdapterBtn.setOnClickListener(this);
        mMultipleArrayAdapterBtn.setOnClickListener(this);
        mBaseAdapterBtn.setOnClickListener(this);
        mListActivityBtn.setOnClickListener(this);
        mRefreshPagingBtn.setOnClickListener(this);
        mCommonAdapterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_entries:
                intent = new Intent(MainActivity.this, EntriesActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_simple_adapter:
                intent = new Intent(MainActivity.this, SimpleAdapterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_array_adapter_single:
                intent = new Intent(MainActivity.this, SingleArrayAdapterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_array_adapter_multiple:
                intent = new Intent(MainActivity.this, MultipleArrayAdapterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_base_adapter:
                intent = new Intent(MainActivity.this, BaseAdapterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_list_activity:
                intent = new Intent(MainActivity.this, MyListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_refresh_paging:
                intent = new Intent(MainActivity.this, RefreshPagingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_common_adapter:
                intent = new Intent(MainActivity.this, CommonAdapterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
