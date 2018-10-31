package com.wangbin.mydome

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.wangbin.mydome.crash.CrashHandler

/**
 * Created by WangBin on 2018/4/16.
 * application
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        CrashHandler.getInstance().init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


    companion object {
        val instance: MyApplication by lazy { MyApplication() }
    }

}