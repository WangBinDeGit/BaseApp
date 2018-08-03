package com.wangbin.mydome.tools

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.wangbin.mydome.Constant
import com.wangbin.mydome.bean.LoginModel

/**
 * Created by WangBin
 * on 2018/4/16.
 */
object PreferencesUtils {
    var PREFERENCE_NAME = "TrineaAndroidCommon"
    var LOGIN_NAME = "LoginRemmberName"

    fun putString(context: Context, key: String, value: String): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    @JvmOverloads
    fun getString(context: Context, key: String, defaultValue: String? = null): String? {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return settings.getString(key, defaultValue)
    }

    fun getStringName(context: Context, key: String, value: String, preference_name: String): Boolean {
        val settings = context.getSharedPreferences(preference_name, MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    @JvmOverloads
    fun getStringName(context: Context, key: String, defaultValue: String? = null, preference_name: String): String? {
        val settings = context.getSharedPreferences(preference_name, MODE_PRIVATE)
        return settings.getString(key, defaultValue)
    }

    fun putInt(context: Context, key: String, value: Int): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor = settings.edit()
        editor.putInt(key, value)
        return editor.commit()
    }

    @JvmOverloads
    fun getInt(context: Context, key: String, defaultValue: Int = -1): Int {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return settings.getInt(key, defaultValue)
    }

    fun putLong(context: Context, key: String, value: Long): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor = settings.edit()
        editor.putLong(key, value)
        return editor.commit()
    }

    @JvmOverloads
    fun getLong(context: Context, key: String, defaultValue: Long = -1L): Long {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return settings.getLong(key, defaultValue)
    }

    fun putFloat(context: Context, key: String, value: Float): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor = settings.edit()
        editor.putFloat(key, value)
        return editor.commit()
    }

    @JvmOverloads
    fun getFloat(context: Context, key: String, defaultValue: Float = -1.0f): Float {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return settings.getFloat(key, defaultValue)
    }

    fun putBoolean(context: Context, key: String, value: Boolean): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    @JvmOverloads
    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return settings.getBoolean(key, defaultValue)
    }

    fun clear(context: Context): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return settings.edit().clear().commit()
    }

    fun clearName(context: Context, preference_name: String): Boolean {
        val settings = context.getSharedPreferences(preference_name, MODE_PRIVATE)
        return settings.edit().clear().commit()
    }

    fun saveUser(context: Context, loginModel: LoginModel) {
        putInt(context, Constant.constant.USER_NO, loginModel.user_no)
        putString(context, Constant.constant.REAL_NAME, loginModel.realname!!)
        putInt(context, Constant.constant.SEX, loginModel.sex)
        putString(context, Constant.constant.AREA_NAME, loginModel.area_name!!)
        putString(context, Constant.constant.STORE_NAME, loginModel.store_name!!)
        putString(context, Constant.constant.ROLE_NAME, loginModel.role_name!!)
        putString(context, Constant.constant.TEL, loginModel.tel!!)
        putString(context, Constant.constant.PHOTO, loginModel.photo!!)
        putString(context, Constant.constant.CDN, loginModel.cdn!!)
        putString(context, Constant.constant.BASE_URL, loginModel.domain!!)
        putString(context, Constant.constant.ACCESS_TOKEN, loginModel.access_token!!)
    }

}

