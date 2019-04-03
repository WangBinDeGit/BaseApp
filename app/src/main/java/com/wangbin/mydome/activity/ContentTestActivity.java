package com.wangbin.mydome.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.wangbin.mydome.R;
import com.wangbin.mydome.tools.RxJavaUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by jxd.
 * on 2018/12/5
 */
public class ContentTestActivity extends Activity {
    private ListView mListView;
    private TextView mTextView_name;
    private TextView mTextView_address;
    private TextView mtextView_age;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenttest);
        mListView = findViewById(R.id.listview);
        mTextView_name = findViewById(R.id.TextView_name);
        mTextView_address = findViewById(R.id.TextView_address);
        mtextView_age = findViewById(R.id.TextView_age);
        ContentResolver reslover = getContentResolver();
        Uri uri = Uri.parse("content://com.sdjxd.rfid.contentprovider.MyContentProvider/person");
        Cursor cursor = reslover.query(uri, null, null, null, null);
        adapter = new SimpleCursorAdapter(ContentTestActivity.this,
                R.layout.activity_contenttest, cursor, new String[]{"name",
                "address", "age"}, new int[]{R.id.TextView_name,
                R.id.TextView_address, R.id.TextView_age});
        mListView.setAdapter(adapter);
        initView();
    }

    @SuppressLint("CheckResult")
    private void initView() {
        RxView.clicks(mTextView_name)
                .compose(RxJavaUtils.preventDuplicateClicksTransformer(5, TimeUnit.SECONDS))
                .subscribe(o -> Toast.makeText(ContentTestActivity.this, "点击了姓名", Toast.LENGTH_SHORT).show());
        RxView.clicks(mTextView_address)
                .compose(RxJavaUtils.preventDuplicateClicksTransformer(2, TimeUnit.SECONDS))
                .subscribe(o -> Toast.makeText(ContentTestActivity.this, "点击了地址", Toast.LENGTH_SHORT).show());
        RxView.clicks(mtextView_age)
                .compose(RxJavaUtils.preventDuplicateClicksTransformer(4, TimeUnit.SECONDS))
                .subscribe(o -> Toast.makeText(ContentTestActivity.this, "点击了年龄", Toast.LENGTH_SHORT).show());
    }

}