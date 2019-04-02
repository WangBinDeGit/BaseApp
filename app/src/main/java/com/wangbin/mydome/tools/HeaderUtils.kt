package com.wangbin.mydome.tools

/**
 * @ClassName HeaderUtils
 * @Description 后台返回数据获取头数据
 * @Author WangBin
 * @Date 2019/3/20 17:59
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