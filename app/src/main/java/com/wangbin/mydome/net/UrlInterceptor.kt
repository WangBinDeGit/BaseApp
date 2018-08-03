package com.wangbin.mydome.net

import com.wangbin.mydome.Constant.Companion.constant
import com.wangbin.mydome.MyApplication
import com.wangbin.mydome.net.Url.Companion.url
import com.wangbin.mydome.tools.CreateMD5
import com.wangbin.mydome.tools.PreferencesUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*


class UrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()
        val token: String
        if (request.url().encodedPath().contains(url.CHECK_USER_INFO) || request.url().encodedPath().contains(url.FORGET_PWD)) {

        } else {
            token = if (request.url().encodedPath().contains(url.LOGIN)) {//登录时的token
                PreferencesUtils.getString(MyApplication.instance()!!.applicationContext, constant.ACCESS_TOKEN_LOGIN)!!
            } else {
                PreferencesUtils.getString(MyApplication.instance()!!.applicationContext, constant.ACCESS_TOKEN)!!
            }
            val time = Date().time.toString()
            val sign = CreateMD5().getMd5(token + time + constant.KEY + constant.IV)

            request = request.newBuilder()
                    .addHeader("access-token", token)
                    .addHeader("timestamp", time)
                    .addHeader("sign", sign!!)
                    .build()
        }

        return chain.proceed(request)
    }
}