package com.wangbin.mydome

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.wangbin.mydome.crash.CrashHandler


/**
 * Created by WangBin on 2018/4/16.
 * application
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashHandler.getInstance().init(this)
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(this, Constant.constant.BuglyID, false)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        //安装tinker
        Beta.installTinker()
    }


    companion object {
        val instance: MyApplication by lazy { MyApplication() }
    }

}