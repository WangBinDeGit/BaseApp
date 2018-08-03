package com.wangbin.mydome.tools

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * Created by WangBin
 * on 2018/8/8.
 */
class HtmlCompat {
    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

}