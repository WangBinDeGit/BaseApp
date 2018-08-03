package com.wangbin.mydome.impl

import com.wangbin.mydome.bean.LoginModel

interface LoginImpl {
    fun loginSuccess(loginModel: LoginModel)
    fun loginFail(loginModel: LoginModel)
}