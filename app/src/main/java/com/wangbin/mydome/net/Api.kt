package com.wangbin.mydome.net

import com.wangbin.mydome.net.Url.Companion.url
import com.wangbin.mydome.net.XApi.Companion.xApi

class Api {
    private var apiService: ApiService? = null
    private var curBaseUrl: String? = null

    private object Holder {
        val INSTANCE = Api()
    }

    companion object {
        val api: Api by lazy { Holder.INSTANCE }
    }

    fun getRetrofitService(): ApiService {
        return getRetrofitService(url.BASE_URL)
    }

    private fun getRetrofitService(baseUrl: String): ApiService {
        synchronized(Api::class.java) {
            if (apiService == null || baseUrl != curBaseUrl) {
                curBaseUrl = baseUrl
                apiService = xApi.getRetrofit(baseUrl).create(ApiService::class.java)
            }
        }
        return apiService!!
    }

}