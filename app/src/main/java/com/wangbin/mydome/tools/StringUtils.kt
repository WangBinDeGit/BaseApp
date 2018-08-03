package com.wangbin.mydome.tools

/**
 * Created by WangBin on 2018/3/7.
 * 判断字符串的值是否为空白单元格或者为空
 */
object StringUtils {
    fun isBlank(str: String?): Boolean {
        return "" === str || null == str
    }
}