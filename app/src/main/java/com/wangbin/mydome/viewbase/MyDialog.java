package com.wangbin.mydome.viewbase;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by WangBin on 2018/8/8.
 * MyDialog
 */
public class MyDialog extends Dialog {
    private int layoutRes;//布局文件
    private Context context;
    private View customView;
    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }
    /**
     * 自定义布局的构造方法
     * @param context
     * @param resLayout
     */
    public MyDialog(Context context, int resLayout){
        super(context);
        this.context = context;
        this.layoutRes=resLayout;
        LayoutInflater inflater= LayoutInflater.from(context);
        customView = inflater.inflate(layoutRes, null);
    }
    /**
     * 自定义主题及布局的构造方法
     * @param context
     * @param theme
     * @param resLayout
     */
    public MyDialog(Context context, int theme, int resLayout){
        super(context, theme);
        this.context = context;
        this.layoutRes=resLayout;
        LayoutInflater inflater= LayoutInflater.from(context);
        customView = inflater.inflate(layoutRes, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(customView);
    }

    public View getCustomView(){
        return customView;
    }
}
