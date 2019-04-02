package com.wangbin.mydome.tools

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @ClassName NetworkAvailable
 * @Description 用于判断是否有网络
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
class NetworkAvailable {

    companion object {

        fun isNetworkAvailable(context: Context): Boolean {
            // 获取手机所有连接管理对象
            val connectivity = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 获取网络连接管理的对象
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected) {
                // 当前网络是连接的
                if (info.state == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true
                }
            }
            return false
        }

    }
}