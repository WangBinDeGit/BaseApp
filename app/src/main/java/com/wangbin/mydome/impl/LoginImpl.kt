package com.wangbin.mydome.impl

import com.wangbin.mydome.bean.BaseEntity
import com.wangbin.mydome.bean.UserBean

interface LoginImpl {
    fun loginSuccess(loginModel: BaseEntity<UserBean>)
    fun loginFail(loginModel: BaseEntity<UserBean>)
    fun toastMsg(toastMsg: String)
}