package com.wangbin.mydome.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.wangbin.mydome.R;

/**
 * @ClassName RecordActivity
 * @Description TODO
 * @Author WangBin
 * @Date 2019/3/6 16:14
 */
public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.rcy_test);
    }
}
