package com.wangbin.mydome.presenter

import android.content.Context
import android.text.TextUtils
import com.wangbin.mydome.Constant
import com.wangbin.mydome.bean.BaseEntity
import com.wangbin.mydome.bean.UserBean
import com.wangbin.mydome.impl.LoginImpl
import com.wangbin.mydome.net.Api.Companion.api
import com.wangbin.mydome.net.BaseObserve
import com.wangbin.mydome.tools.AesUtils
import com.wangbin.mydome.tools.PreferencesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/***
 * 登录
 */
class LoginPresenter(val impl: LoginImpl, val context: Context) : LoginPresenterinterface {

    override
    fun login(userName: String, pwd: String, checked: Boolean) {
        if (TextUtils.isEmpty(userName)) {
            impl.toastMsg("请输入用户名")
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            impl.toastMsg("请输入密码")
            return
        }
        PreferencesUtils.putString(context, Constant.constant.USER_NAME, userName)
        if (checked) {
            PreferencesUtils.putString(context, Constant.constant.USER_PWD, pwd)
        } else {
            PreferencesUtils.putString(context, Constant.constant.USER_PWD, "")
        }
        val token = AesUtils.encrypt("$userName-$pwd", "utf-8", Constant.constant.KEY, Constant.constant.IV)
        PreferencesUtils.putString(context, Constant.constant.ACCESS_TOKEN_LOGIN, token)
        api.getRetrofitService().login(userName, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserve<BaseEntity<UserBean>>(context, true) {
                    override fun onFail(t: BaseEntity<UserBean>) {
                        impl.loginFail(t)
                    }

                    override fun onSuccess(t: BaseEntity<UserBean>) {
                        impl.loginSuccess(t)
                    }
                })
    }
}