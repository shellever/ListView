package com.shellever.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class EntriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        ListView mEntriesLv = (ListView) findViewById(R.id.lv_entries);
        mEntriesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String names[] = getResources().getStringArray(R.array.names);
                Toast.makeText(EntriesActivity.this, names[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
