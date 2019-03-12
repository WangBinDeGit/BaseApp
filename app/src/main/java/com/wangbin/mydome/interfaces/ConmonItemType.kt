package com.wangbin.mydome.interfaces

/**
 * @ClassName ConmonItemType
 * @Description TODO
 * @Author WangBin
 * @Date 2019/2/21 16:27
 */
interface ConmonItemType<T> {
    fun getLayoutId(itemType: Int): Int  //不同的Item的布局

    fun getItemViewType(position: Int, t: T): Int  //type
}
