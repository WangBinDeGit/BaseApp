package com.wangbin.mydome.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangbin.mydome.R;

/**
 * Created by wangbin on 2018/8/2.
 * LoadingDialog
 */
public class LoadingDialog {

    /**
     * 得到自定义的progressDialog
     *
     * @param context 上下文
     * @param msg     显示文字
     * @return 返回Dialog
     */
    public static Dialog initProgressDialog(Context context, String msg) {
        return initProgressDialog(context, msg, false);
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context 上下文
     * @param msg     显示文字
     * @return 返回Dialog
     */
    public static Dialog initProgressDialog(Context context, String msg, boolean cancelable) {

        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_view,new LinearLayout(context),false);
        // 获取整个布局
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        // 页面中的Img
        ImageView img = view.findViewById(R.id.img);
        // 页面中显示文本
        TextView tipText = view.findViewById(R.id.tipTextView);

        // 加载动画，动画用户使img图片不停的旋转
        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.animation_view);
        // 显示动画
        img.startAnimation(animation);
        // 显示文本
        tipText.setText(msg);

        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        loadingDialog.setCancelable(cancelable);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;

    }

}