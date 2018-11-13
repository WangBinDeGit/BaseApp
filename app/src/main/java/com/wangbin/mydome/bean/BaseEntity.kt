package com.wangbin.mydome.bean

/**
 * Created by jxd.
 * on 2018/11/13
 */
class BaseEntity<T>{
    var statusCode: Int = 0
    var message: String= ""
    var data: T? = null
}
