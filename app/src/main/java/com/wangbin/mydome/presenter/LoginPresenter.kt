package com.wangbin.mydome.presenter

import android.text.TextUtils
import com.wangbin.mydome.bean.BaseEntity
import com.wangbin.mydome.bean.UserBean
import com.wangbin.mydome.impl.LoginImpl
import com.wangbin.mydome.net.Api.Companion.api
import com.wangbin.mydome.net.BaseObserve
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/***
 * 登录
 */
class LoginPresenter(val impl: LoginImpl) : LoginPresenterinterface {

    override
    fun login(userName: String, pwd: String) {
        if (TextUtils.isEmpty(userName)) {
            impl.toastMsg("请输入用户名")
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            impl.toastMsg("请输入密码")
            return
        }
        api.getRetrofitService().login(userName, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserve<BaseEntity<UserBean>>() {
                    override fun onFail(t: BaseEntity<UserBean>) {
                        impl.loginFail(t)
                    }

                    override fun onSuccess(t: BaseEntity<UserBean>) {
                        impl.loginSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message.toString().contains("Failed to connect to")) {
                            impl.toastMsg("服务器错误,请重试!")
                            return
                        }
                        impl.toastMsg(e.message.toString())
                    }
                })
    }
}