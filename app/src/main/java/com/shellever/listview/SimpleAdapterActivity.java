package com.shellever.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleAdapterActivity extends AppCompatActivity {

    private Integer[] itemIconIds = {
            R.drawable.agnes_overjoyed_girl,
            R.drawable.angry_minion_cartoon,
            R.drawable.curious_minion_cartoon,
            R.drawable.dancing_minion_cartoon,
            R.drawable.despicable_me_minion_cartoon,
            R.drawable.happy_minion_cartoon,
            R.drawable.kungfu_minion_cartoon,
            R.drawable.shy_minion_cartoon
    };

    private String[] itemTitles = {
            "agnes_overjoyed_girl",
            "angry_minion_cartoon",
            "curious_minion_cartoon",
            "dancing_minion_cartoon",
            "despicable_me_minion_cartoon",
            "happy_minion_cartoon",
            "kungfu_minion_cartoon",
            "shy_minion_cartoon"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);

        ListView mSimpleAdapterLv = (ListView) findViewById(R.id.lv_simple_adapter);

        ArrayList<HashMap<String, Object>> itemsList = new ArrayList<>();
        for (int i = 0; i < itemIconIds.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemIconId", itemIconIds[i]);
            map.put("ItemTitle", itemTitles[i]);
            itemsList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,                                               // Context context
                itemsList,                                          // List<? extends Map<String, ?>> data
                R.layout.simple_adapter_list_item,                  // int resId
                new String[]{"ItemIconId", "ItemTitle"},            // String[] from
                new int[]{R.id.iv_item_icon, R.id.tv_item_title}    // int[] to
        );

        mSimpleAdapterLv.setAdapter(adapter);

        mSimpleAdapterLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.tv_item_title);
                Toast.makeText(SimpleAdapterActivity.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
