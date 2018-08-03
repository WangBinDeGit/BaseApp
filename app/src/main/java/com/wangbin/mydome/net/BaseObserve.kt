package com.meijiamanage.net

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.wangbin.mydome.tools.NetworkAvailable
import com.wangbin.mydome.tools.ToastUtils
import com.wangbin.mydome.tools.DUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONObject


abstract class BaseObserve<T>(context: Context, isShowDialog: Boolean) : Observer<T> {

    val context = context
    val isShowDialog = isShowDialog

    override fun onComplete() {
        if (isShowDialog)
            DUtils.dismiss()
    }

    override fun onSubscribe(d: Disposable) {
        if (!NetworkAvailable().isNetworkAvailable(context)) {
            ToastUtils.showShortToast(context, "网络未连接，请检查网络")
            d.dispose()
            return
        } else {
            if ((null == DUtils.dialog || !DUtils.dialog.isShowing) && isShowDialog)
                DUtils.show(context)
        }
    }

    override fun onNext(t: T) {
        val jsonElement = JsonParser().parse(Gson().toJson(t))
        if (jsonElement.isJsonArray) {//数组
            onSuccess(t)
        } else {
            val jsonObject = jsonElement.asJsonObject
            if (jsonObject.has("statusCode") && JSONObject(Gson().toJson(t)).getInt("statusCode") == 0) {//数据返回失败
                onFail(t)
            } else
                onSuccess(t)
        }


    }

    override fun onError(e: Throwable) {
        ToastUtils.showShortToast(context, e.message.toString())
        if (isShowDialog)
            DUtils.dismiss()
    }


    abstract fun onSuccess(t: T)

    abstract fun onFail(t: T)
}