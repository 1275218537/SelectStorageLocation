package com.hndist.selectstoragelocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    //private String selectStr = "";//可以用于传递选择的存储位置
    private List<String> strAllPath;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strAllPath = FilesUtils.getAllSdPaths(this);
        Log.e("TAG", "strAllPath = " + strAllPath);
        mListView = findViewById(R.id.lv_path);
        adapter = new MyAdapter(strAllPath);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "您选择的存储位置是：" + strAllPath.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
