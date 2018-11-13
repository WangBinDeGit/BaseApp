package com.wangbin.mydome.bean

/**
 * Created by WangBin on 2018/10/31
 */
class UpdateModel {
    //（必须）是否更新
    var update: String? = null
    //（必须）新版本号，
    var new_version: String? = null
    //（必须）下载地址
    var apk_file_url: String? = null
    //（必须）更新内容
    var update_log: String? = null
    //是否强制更新，可以不设置
    var constraint: String? = null
    //大小，不设置不显示大小，可以不设置
    var target_size: String? = null
    var new_md5: String? = null
}
