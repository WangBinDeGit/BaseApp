package com.wangbin.mydome.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.wangbin.mydome.Constant.Companion.constant
import com.wangbin.mydome.R
import com.wangbin.mydome.base.BaseActivity
import com.wangbin.mydome.bean.LoginModel
import com.wangbin.mydome.impl.LoginImpl
import com.wangbin.mydome.presenter.LoginPresenter
import com.wangbin.mydome.presenter.LoginPresenterinterface
import com.wangbin.mydome.tools.PreferencesUtils
import com.wangbin.mydome.view.ClearEditText
import kotlinx.android.synthetic.main.activity_login.*

/***
 * 登录
 */
class LoginActivity : BaseActivity(), LoginImpl {

    private var loginPersenter: LoginPresenterinterface? = null

    override fun initParams(arguments: Bundle?) {
    }

    override fun intiLayout(): Int {
        return R.layout.activity_login
    }

    @SuppressLint("WrongViewCast")
    override fun initView() {
        findViewById<TextView>(R.id.tv_forget).paint.flags = Paint.UNDERLINE_TEXT_FLAG
        findViewById<ClearEditText>(R.id.et_login_user).setText(PreferencesUtils.getString(this, constant.USER_NAME))
        if (findViewById<CheckBox>(R.id.cb_remember).isChecked) {
            findViewById<ClearEditText>(R.id.et_login_password).setText(PreferencesUtils.getString(this, constant.USER_PWD))
        }
        loginPersenter = LoginPresenter(this, this)
    }

    override fun setListener() {
        onClickView(tv_login_submit)
        onClickView(cb_remember)
        onClickView(tv_forget)
    }

    override fun widgetClick(view: View) {
        when (view.id) {
            R.id.tv_login_submit -> {
                if (TextUtils.isEmpty(et_login_user.text.toString())) {
                    toastShort("请输入用户名")
                    return
                }
                if (TextUtils.isEmpty(et_login_password.text.toString())) {
                    toastShort("请输入密码")
                    return
                }
                PreferencesUtils.putString(this, constant.USER_NAME, getText(et_login_user))
                if (cb_remember.isChecked) {
                    PreferencesUtils.putString(this, constant.USER_PWD, getText(et_login_password))
                } else {
                    PreferencesUtils.putString(this, constant.USER_PWD, "")
                }
                loginPersenter!!.login(getText(et_login_user), getText(et_login_password))
            }
            R.id.cb_remember -> {

            }
            R.id.tv_forget -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun loginSuccess(loginModel: LoginModel) {
        //保存用户信息
        PreferencesUtils.saveUser(this@LoginActivity, loginModel)
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }


    override fun loginFail(loginModel: LoginModel) {
        toastShort(loginModel.message!!)
    }

}
