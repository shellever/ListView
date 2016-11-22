package com.shellever.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MultipleArrayAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_array_adapter);

        ListView mMultipleArrayAdapterLv = (ListView) findViewById(R.id.lv_multiple_array_adapter);

        String[] names = getResources().getStringArray(R.array.names);

        // 多选模式
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, names);
        mMultipleArrayAdapterLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        mMultipleArrayAdapterLv.setAdapter(adapter);
    }
}
