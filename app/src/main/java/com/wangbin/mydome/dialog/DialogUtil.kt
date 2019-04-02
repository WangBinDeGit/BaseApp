package com.wangbin.mydome.dialog

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

/**
 * @ClassName DialogUtil
 * @Description TODO
 * @Author WangBin
 * @Date 2019/3/22 15:26
 */
class DialogUtil {

    /**
     * 统一弹窗
     *
     * @param message 展示信息
     * @param title   标题
     */
    fun alertText(context: Context, title: String, message: String, clickListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", clickListener)
                .setNegativeButton("取消", clickListener)
                .show()
    }
}