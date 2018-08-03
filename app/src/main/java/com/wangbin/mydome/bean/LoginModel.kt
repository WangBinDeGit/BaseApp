package com.wangbin.mydome.bean

/**
 * Created by  wangbin.
 * on 2018/8/2
 */
class LoginModel : BaseModel() {
    /**
    "user_no":10003,
    "realname":"张伟",
    "sex":1,
    "area_id":55,
    "store_id":2,
    "role_id":3,
    "leader_name":"张伟",
    "role_name":"副总",
    "area_name":"西部片区",
    "store_name":"西站门店",
    "tel":"13255686712",
    "photo":"1.jpg",
    "access_token":"_unTJjy1zfUE51D4yHMzMc-FjrIzpCgb",
    "domain":"http://a.happymeijia.cn/",
    "cdn":"http://image.happymeijia.cn/",
    "statusCode":1
    }
     */
    var user_no: Int = 0
    var realname: String? = null
    var sex: Int = 0
    var area_id: Int = 0
    var store_id: Int = 0
    var role_id: Int = 0
    var leader_name: String? = null
    var role_name: String? = null
    var area_name: String? = null
    var store_name: String? = null
    var tel: String? = ""
    var photo: String? = ""
    var access_token: String? = null
    var domain: String? = ""
    var cdn: String? = ""
}