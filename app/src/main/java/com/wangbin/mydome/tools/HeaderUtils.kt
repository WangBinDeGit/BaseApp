package com.wangbin.mydome.tools

/**
 * Created by WangBin
 * on 2018/1/12.
 */
object HeaderUtils {

    val MAXPAGE = "X-Pagination-Page-Count"
    val MAXCOUNT = "X-Pagination-Total-Count"
    val PER_PAGE = "X-Pagination-Per-Page"
    val CURRENT_PAGE = "X-Pagination-Current-Page"
    var maxpage = 1
    var maxCount = 1
    var per_page = 1
    var current_page = 1
}