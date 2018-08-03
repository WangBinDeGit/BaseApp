package com.wangbin.mydome

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * Created by WangBin on 2018/4/16.
 * application
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: Application? = null
        fun instance() = instance
    }

}