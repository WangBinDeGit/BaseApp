package com.wangbin.mydome.tools

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * @ClassName HtmlCompatUtil
 * @Description 后台返回数据显示时转换html格式显示
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
class HtmlCompatUtil {

    companion object {

        /**
         * 转换为html格式返回
         * @param source 传入原字符
         * @return  Spanned转换后的格式
         */
        fun fromHtml(source: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }
    }

}