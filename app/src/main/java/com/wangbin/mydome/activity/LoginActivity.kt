package com.wangbin.mydome.activity

import android.content.ComponentName
import android.content.pm.PackageManager
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import com.wangbin.mydome.Constant
import com.wangbin.mydome.Constant.Companion.constant
import com.wangbin.mydome.R
import com.wangbin.mydome.base.BaseActivity
import com.wangbin.mydome.bean.BaseEntity
import com.wangbin.mydome.bean.UserBean
import com.wangbin.mydome.impl.LoginImpl
import com.wangbin.mydome.presenter.LoginPresenter
import com.wangbin.mydome.presenter.LoginPresenterinterface
import com.wangbin.mydome.tools.NetworkAvailable
import com.wangbin.mydome.tools.PreferencesUtils
import com.wangbin.mydome.tools.encryption.AesUtils
import com.wangbin.mydome.view.ClearEditText
import kotlinx.android.synthetic.main.activity_login.*


/***
 * 登录
 */
class LoginActivity : BaseActivity(), LoginImpl {
    private var mPm: PackageManager? = null
    private var userName = ""
    private var pwd = ""

    override fun toastMsg(toastMsg: String) {
        mToastUtils!!.showShortToast(this,toastMsg)
    }

    private var loginPersenter: LoginPresenterinterface? = null

    override fun intiLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        findViewById<TextView>(com.wangbin.mydome.R.id.tv_forget).paint.flags = Paint.UNDERLINE_TEXT_FLAG
        findViewById<ClearEditText>(com.wangbin.mydome.R.id.et_login_user).setText(PreferencesUtils.getString(this, constant.USER_NAME))
        if (cb_remember.isChecked) {
            et_login_password.setText(PreferencesUtils.getString(this, constant.USER_PWD))
        }
        loginPersenter = LoginPresenter(this)
    }

    override fun setListener() {
        notFastClick(tv_login_submit)
        onClickView(tv_forget)
    }

    override fun widgetClick(view: View) {
        when (view.id) {
            com.wangbin.mydome.R.id.tv_login_submit -> {
                userName = getText(et_login_user)
                pwd = getText(et_login_password)
                PreferencesUtils.putString(this, Constant.constant.USER_NAME, userName)
                if (cb_remember.isChecked) {
                    PreferencesUtils.putString(this, Constant.constant.USER_PWD, pwd)
                } else {
                    PreferencesUtils.putString(this, Constant.constant.USER_PWD, "")
                }
                if (!NetworkAvailable.isNetworkAvailable(this)) {
                    mToastUtils!!.showShortToast(this, "网络未连接，请检查网络")
                    return
                }
                loginPersenter!!.login(userName, pwd)
            }
            com.wangbin.mydome.R.id.tv_forget -> {
                toastMsg("有没有热修复，这个是新的")
                startActivity(TestActivity::class.java)
            }
        }
    }

    override fun loginSuccess(loginModel: BaseEntity<UserBean>) {
        //保存用户信息
        if (loginModel.statusCode == 200) {
            val token = AesUtils.encrypt("$userName-$pwd", "utf-8", Constant.constant.KEY, Constant.constant.IV)
            PreferencesUtils.putString(this, Constant.constant.ACCESS_TOKEN_LOGIN, token)
            if (loginModel.data != null) PreferencesUtils.saveUser(this@LoginActivity, loginModel.data!!)
            startActivity(MainActivity::class.java)
            finish()
        } else {
            mToastUtils!!.showShortToast(this,loginModel.message)
        }
    }

    override fun loginFail(loginModel: BaseEntity<UserBean>) {
        mToastUtils!!.showShortToast(this,loginModel.message)
    }

    override fun onDestroy() {
        initLauncherIcon()
        super.onDestroy()
    }

    private fun initLauncherIcon() {
        val mDefault = componentName
        val mEday11 = ComponentName(
                baseContext,
                "com.wangbin.mydome.Test11")
        val mEday65 = ComponentName(
                baseContext,
                "com.wangbin.mydome.Test12")
        mPm = applicationContext.packageManager
        if (1552537854000L < System.currentTimeMillis()) {
            changeIcon65(mDefault, mEday11)
            return
        }
        if (1552537854000L < System.currentTimeMillis()) {
            changeIcon65(mDefault, mEday65)
            return
        }
    }

    fun changeIcon65(disable: ComponentName, enable: ComponentName) {
        disableComponent(disable)
        enableComponent(enable)
    }

    private fun enableComponent(componentName: ComponentName) {
        mPm!!.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
    }

    private fun disableComponent(componentName: ComponentName) {
        mPm!!.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
    }
}
