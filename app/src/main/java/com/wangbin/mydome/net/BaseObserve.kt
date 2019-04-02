package com.wangbin.mydome.net

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.wangbin.mydome.tools.LogUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONObject


abstract class BaseObserve<T> : Observer<T> {

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
        val jsonElement = JsonParser().parse(Gson().toJson(t))
        if (jsonElement.isJsonArray) {//数组或对象
            onSuccess(t)
        } else {
            val jsonObject = jsonElement.asJsonObject
            if (jsonObject.has("statusCode") && JSONObject(Gson().toJson(t)).getInt("statusCode") != 200) {//数据返回失败
                onFail(t)
            } else
                onSuccess(t)
        }
    }

    override fun onError(e: Throwable) {
        LogUtil.e("请求失败：",e.message)
    }

    abstract fun onSuccess(t: T)

    abstract fun onFail(t: T)
}