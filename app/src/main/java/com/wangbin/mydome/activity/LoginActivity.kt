package com.wangbin.mydome.activity

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import com.wangbin.mydome.Constant.Companion.constant
import com.wangbin.mydome.R
import com.wangbin.mydome.base.BaseActivity
import com.wangbin.mydome.bean.BaseEntity
import com.wangbin.mydome.bean.UserBean
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
    override fun toastMsg(toastMsg: String) {
        toastShort(toastMsg)
    }

    private var loginPersenter: LoginPresenterinterface? = null

    override fun intiLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        findViewById<TextView>(R.id.tv_forget).paint.flags = Paint.UNDERLINE_TEXT_FLAG
        findViewById<ClearEditText>(R.id.et_login_user).setText(PreferencesUtils.getString(this, constant.USER_NAME))
        if (cb_remember.isChecked) {
            et_login_password.setText(PreferencesUtils.getString(this, constant.USER_PWD))
        }
        loginPersenter = LoginPresenter(this, this)
    }

    override fun setListener() {
        notFastClick(tv_login_submit)
        onClickView(tv_forget)
    }

    override fun widgetClick(view: View) {
        when (view.id) {
            R.id.tv_login_submit -> loginPersenter!!.login(getText(et_login_user), getText(et_login_password), cb_remember.isChecked)
            R.id.tv_forget -> startActivity(TestActivity::class.java)
        }
    }

    override fun loginSuccess(loginModel: BaseEntity<UserBean>) {
        //保存用户信息
        if (loginModel.statusCode == 200) {
            if (loginModel.data != null) PreferencesUtils.saveUser(this@LoginActivity, loginModel.data!!)
            startActivity(MainActivity::class.java)
            finish()
        } else {
            toastShort(loginModel.message)
        }
    }

    override fun loginFail(loginModel: BaseEntity<UserBean>) {
        toastShort(loginModel.message)
    }

}
