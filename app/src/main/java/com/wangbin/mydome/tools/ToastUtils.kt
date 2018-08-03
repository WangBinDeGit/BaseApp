package com.wangbin.mydome.tools

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.wangbin.mydome.Constant.Companion.constant

/**
 * Created by WangBin on 2018/1/11.
 *
 * ToastUtils
 */
@SuppressLint("StaticFieldLeak")
object ToastUtils {
    private var toast: Toast? = null

    private var view: View? = null

    @SuppressLint("ShowToast")
    private fun getToast(context: Context) {
        if (toast == null) {
            toast = Toast(context)
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).view
        }
        toast!!.view = view
    }

    fun showShortToast(context: Context, msg: CharSequence) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT)
    }

    fun showShortToast(context: Context, resId: Int) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_SHORT)
    }

    fun showLongToast(context: Context, msg: CharSequence) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT)
    }

    fun showLongToast(context: Context, resId: Int) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_LONG)
    }

    private fun showToast(context: Context, msg: CharSequence, duration: Int) {
        try {
            getToast(context)
            toast!!.setText(msg)
            toast!!.duration = duration
            toast!!.setGravity(Gravity.CENTER, 0, 0)
            toast!!.show()
        } catch (e: Exception) {
            Log.e(constant.APP_NAME, e.message)
        }

    }

    private fun showToast(context: Context, resId: Int, duration: Int) {
        try {
            if (resId == 0) {
                return
            }
            getToast(context)
            toast!!.setText(resId)
            toast!!.duration = duration
            toast!!.setGravity(Gravity.CENTER, 0, 0)
            toast!!.show()
        } catch (e: Exception) {
            Log.e(constant.APP_NAME, e.message)
        }

    }

    fun cancelToast() {
        if (toast != null) {
            toast!!.cancel()
        }
    }

}