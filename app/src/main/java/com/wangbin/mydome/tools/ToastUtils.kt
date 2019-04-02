package com.wangbin.mydome.tools

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
class ToastUtils {
    private var mToastUtils: ToastUtils? = null
    private var mToast: Toast? = null
    private var view: View? = null

    /**
     * 单例
     *
     * @return AppActivityStack
     */
    @Synchronized
    fun create(): ToastUtils {
        if (null == mToastUtils) {
            synchronized(AppActivityStack::class.java) {
                if (null == mToastUtils) {
                    mToastUtils = ToastUtils()
                }
            }
        }
        return mToastUtils!!
    }

    private fun getToast(context: Context) {
        if (mToast == null) {
            mToast = Toast(context)
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).view
        }
        mToast!!.view = view
    }

    fun showShortToast(context: Context, msg: CharSequence) {
        showToast(context, msg, Toast.LENGTH_SHORT)
    }

    fun showShortToast(context: Context, resId: Int) {
        showToast(context, resId, Toast.LENGTH_SHORT)
    }

    fun showLongToast(context: Context, msg: CharSequence) {
        showToast(context, msg, Toast.LENGTH_SHORT)
    }

    fun showLongToast(context: Context, resId: Int) {
        showToast(context, resId, Toast.LENGTH_LONG)
    }

    private fun showToast(context: Context, msg: CharSequence, duration: Int) {
        try {
            getToast(context)
            mToast!!.setText(msg)
            mToast!!.duration = duration
            mToast!!.setGravity(Gravity.CENTER, 0, 0)
            mToast!!.show()
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
            mToast!!.setText(resId)
            mToast!!.duration = duration
            mToast!!.setGravity(Gravity.CENTER, 0, 0)
            mToast!!.show()
        } catch (e: Exception) {
            Log.e(constant.APP_NAME, e.message)
        }
    }

    /**
     * 取消toast，在activity的destory方法中调用
     */
    fun destory() {
        if (null != mToast) {
            mToast!!.cancel()
            mToast = null
        }
        mToastUtils = null
    }

}