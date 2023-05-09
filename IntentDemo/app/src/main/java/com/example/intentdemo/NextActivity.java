package com.example.intentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NextActivity extends AppCompatActivity {
    Bundle bundle;
    private List<Map<String, Object>> list;
    private ListView listView;

    // key值数组，适配器通过key值取value，与列表项组件一一对应
    String[] from = { "img", "text"};
    // 列表项组件Id 数组
    int[] to = { R.id.image, R.id.text};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        bundle = getIntent().getBundleExtra("bundle");
        List<String> data = new ArrayList<>();
        data.add(bundle.getString("number"));
        data.add(bundle.getString("name"));
        data.add(bundle.getString("sex"));
        data.add(bundle.getString("academy"));
        data.add(bundle.getString("major"));
        data.add(bundle.getString("class"));
        initDataList(data);
        listView = findViewById(R.id.listview);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.list_item, from, to);
        listView.setAdapter(simpleAdapter);
    }

    private void initDataList(List<String> data) {
        int[] resources = {R.drawable.credit_card_black, R.drawable.account_box_black, R.drawable.wc_black,
                R.drawable.account_balance_black, R.drawable.school_black, R.drawable.assignment_ind_black};
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", resources[i]);
            map.put("text", data.get(i));
            list.add(map);
        }
    }
}