package com.wangbin.mydome.bean

/**
 * Created by  wangbin.
 * on 2018/8/2
 */

open class SingleTitleModel() {


    var title: String? = null  //显示数据拼音的首字母

    constructor(title: String) : this() {
        this.title = title
    }

}