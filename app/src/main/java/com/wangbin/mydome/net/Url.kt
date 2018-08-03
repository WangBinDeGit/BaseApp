package com.wangbin.mydome.net

/**
 * 接口地址
 */
class Url {
    private object Holder {
        val INSTANCE = Url()
    }

    companion object {
        val url: Url by lazy { Holder.INSTANCE }
    }

    val BASE_URL = " http://a.happymeijia.cn/"
    val LOGIN="v1/login"//登录
    val CHECK_USER_INFO="v1/user/rp-check"//忘记密码的第一个页面
    val FORGET_PWD="v1/user/set-password"//忘记密码的第二个页面 --设置密码

}