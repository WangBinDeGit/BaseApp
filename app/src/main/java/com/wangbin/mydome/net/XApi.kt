package com.wangbin.mydome.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
/**
 * XApi
 */
class XApi {

    private object Holder {
        val INSTANCE = XApi()
    }

    companion object {
        val xApi: XApi by lazy { Holder.INSTANCE }
    }

    fun getRetrofit(baseUrl: String): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)//打印retrofit日志
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create()) //增加返回值为Gson的支持(以实体类返回)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .baseUrl(baseUrl)
                .build()
    }

    private fun getClient(): OkHttpClient? {
        return OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(UrlInterceptor())
                .addInterceptor(LogInterceptor())
                .build()
    }

}