package com.wangbin.mydome.presenter

import android.content.Context
import com.wangbin.mydome.Constant
import com.wangbin.mydome.impl.LoginImpl
import com.wangbin.mydome.net.Api.Companion.api
import com.meijiamanage.net.BaseObserve
import com.wangbin.mydome.tools.PreferencesUtils
import com.wangbin.mydome.bean.LoginModel
import com.wangbin.mydome.tools.AesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/***
 * 登录
 */
class LoginPresenter(val impl: LoginImpl, val context: Context):LoginPresenterinterface {

    override
    fun login(userName: String, pwd: String) {
        val token = AesUtils.encrypt("$userName-$pwd", "utf-8", Constant.constant.KEY, Constant.constant.IV)
        PreferencesUtils.putString(context, Constant.constant.ACCESS_TOKEN_LOGIN, token)
        api.getRetrofitService().login()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserve<LoginModel>(context, true) {
                    override fun onFail(t: LoginModel) {
                        impl.loginFail(t)
                    }

                    override fun onSuccess(t: LoginModel) {
                        impl.loginSuccess(t)
                    }
                })
    }
}