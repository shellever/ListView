package com.shellever.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SingleArrayAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_array_adapter);

        ListView mSingleArrayAdapterLv = (ListView) findViewById(R.id.lv_single_array_adapter);

        String[] names = getResources().getStringArray(R.array.names);

        // 单选模式
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, names);
        mSingleArrayAdapterLv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mSingleArrayAdapterLv.setAdapter(adapter);
    }
}
