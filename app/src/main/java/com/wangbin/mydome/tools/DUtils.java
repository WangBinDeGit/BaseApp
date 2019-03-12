package com.wangbin.mydome.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.wangbin.mydome.R;
import com.wangbin.mydome.viewbase.FadingCircle;
import com.wangbin.mydome.viewbase.MyDialog;
import com.wangbin.mydome.viewbase.Sprite;

/**
 * Created by WangBin on 2016/4/1.
 * 加载转圈
 */
public class DUtils {
    @SuppressLint("StaticFieldLeak")
    public static MyDialog dialog = null;
    @SuppressLint("StaticFieldLeak")
    private static MyDialog dialogfile = null;

    public static void show(Context context) {
        dialog = new MyDialog(context, R.style.customDialog, R.layout.mydialog);
        View view = dialog.getCustomView();
        Button button = (Button) view.findViewById(R.id.button);
        Sprite wave = new FadingCircle();
        //控制动画的大小
        wave.setBounds(0, 0,
                100,
                100);
//                wave.setColor(getResources().getColor(R.color.colorAccent));
        wave.setColor(0xff75c1f1);
        button.setCompoundDrawables(wave, null, null, null);
        wave.start();
        dialog.show();
    }

    public static void show(Context context, Boolean isCanTouch) {
        dialog = new MyDialog(context, R.style.customDialog, R.layout.mydialog);
        View view = dialog.getCustomView();
        Button button = (Button) view.findViewById(R.id.button);
        Sprite wave = new FadingCircle();
        //控制动画的大小
        wave.setBounds(0, 0,
                100,
                100);
//                wave.setColor(getResources().getColor(R.color.colorAccent));
        wave.setColor(0xff75c1f1);
        button.setCompoundDrawables(wave, null, null, null);
        wave.start();
        dialog.setCanceledOnTouchOutside(isCanTouch);
        dialog.show();
    }

    public static void ShowFile(Context context) {
        dialogfile = new MyDialog(context, R.style.customDialog, R.layout.myfiledialog);
        View view = dialogfile.getCustomView();
        Button button = (Button) view.findViewById(R.id.button);
        Sprite wave = new FadingCircle();
        //控制动画的大小
        wave.setBounds(0, 0,
                100,
                100);
//                wave.setColor(getResources().getColor(R.color.colorAccent));
        wave.setColor(0xff75c1f1);
        button.setCompoundDrawables(wave, null, null, null);
        wave.start();
        dialogfile.setCanceledOnTouchOutside(false);
        dialogfile.show();
    }

    public static void dismiss() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (dialogfile != null) {
                        dialogfile.dismiss();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
