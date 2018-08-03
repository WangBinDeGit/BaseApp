package com.wangbin.mydome

/**
 * 常量
 */
class Constant {

    private object Holder {
        val INSTANCE = Constant()
    }

    companion object {
        val constant: Constant by lazy { Holder.INSTANCE }
    }

    val APP_NAME = "MyApp"
    val TAG = "MyApp"

    var KEY = "e58824997616e958"
    var IV = "9f99a91e98f20393"


    //--------------------Handler 通知code -----------------
    val ZERO = 0x0000

    val ONE = 0x0001

    val TWO = 0x0002

    val THREE = 0x0003

    val FOUR = 0x0004

    val FIVE = 0x0005

    val SIX = 0x0006

    val SEVEN = 0x0007

    val EIGHT = 0x0008

    val NINE = 0x0009

    val FAILPATH = 404

    val RunTime = 1500

    val TIME_INTERVAL = 2000

    val FIRST = "first"//第一次打开App

    val ACCESS_TOKEN_LOGIN = "access_token_login"//登录时形成的token

    val PWD_TYPE = "pwd_type"

    val FORGET_PWD = 1//忘记密码

    val CHANGE_PWD = 2//修改密码

    /**
     * 存储字段
     */
    val USER_NAME = "user_name"
    val USER_PWD = "user_pwd"
    val BASE_URL = "base_url"//
    val ACCESS_TOKEN = "access_token"
    val USER_NO = "user_no"
    val REAL_NAME = "real_name"
    val SEX = "sex"
    val AREA_NAME = "area_name"
    val STORE_NAME = "store_name"
    val ROLE_NAME = "role_name"
    val TEL = "tel"//电话
    val PHOTO = "photo"//头像
    val DOMAIN = "domain"//域名
    val CDN = "cdn"//CDN地址-图片域名

    /**
     * 公共参数
     */
    val PARAM_ACCESS_TOKEN = "access-token"
    val PARAM_TIMESTAMP = "timestamp"
    val PARAM_SIGN = "sign"

    /**
     * 固定死的参数字段VALUE
     */
    object Param {
        val DECORATION = ""
    }

    /**
     * 页面跳转标志
     */
    object Action {
        val DECORATION = ""
    }

    /**
     * eventBus 发送事件的Code
     */
    object Event {
        val LOGIN_SUCCESS = 1 //登录成功
    }

    /**
     * startActivityForResult发送的Code
     */
    object IntentCode {
        val ID_CARD_REQUEST = 100//身份认证请求码
        val ID_CARD_RESULT = 101//身份认证返回码
    }
}