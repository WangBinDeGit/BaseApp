package com.wangbin.mydome.impl

import com.wangbin.mydome.bean.ResultModel

interface LoginImpl {
    fun loginSuccess(loginModel: ResultModel)
    fun loginFail(loginModel: ResultModel)
    fun toastMsg(toastmsg: String)
}