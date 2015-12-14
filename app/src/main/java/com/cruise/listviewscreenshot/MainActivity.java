package com.cruise.listviewscreenshot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ScreenShotTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mListView = (ListView) findViewById(R.id.lv_target);

        setSupportActionBar(toolbar);

        initView();
        initData();
    }

    private void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask = new ScreenShotTask(MainActivity.this, mListView);
                mTask.execute();
            }
        });
    }

    private void initData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            data.add("测试数据 " + i);
        }

        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, data));
    }

}
