package com.wangbin.mydome.adapter

import android.content.Context
import com.wangbin.mydome.Constant
import com.wangbin.mydome.R
import com.wangbin.mydome.base.BaseViewHolder
import com.wangbin.mydome.interfaces.ConmonItemType

/**
 * @ClassName TestAdapter1
 * @Description TODO
 * @Author WangBin
 * @Date 2019/2/21 16:54
 */
internal class TestAdapter1(mContext: Context, mData: List<String>) : MultiItemCommonAdapter<String>(mContext, mData) {
    val conmonItemType = object : ConmonItemType<String> {
        override fun getLayoutId(itemType: Int): Int {
            when (itemType) {
                Constant.constant.ONE -> return R.layout.item_search
                Constant.constant.TWO -> return R.layout.layout_recyclerview_item_view
            }
            return R.layout.item_search
        }

        override fun getItemViewType(position: Int, t: String): Int {
            when (t) {
                Constant.constant.APP_NAME -> return Constant.constant.ONE
                Constant.constant.BuglyID -> return Constant.constant.TWO
            }
            return Constant.constant.ONE
        }
    }

    override fun addConmonItemType(): ConmonItemType<String> {
        return conmonItemType
    }

    override fun onBindData(holder: BaseViewHolder, bean: String, position: Int) {
        when (bean) {
            Constant.constant.APP_NAME ->
                holder.setText(R.id.item_search, "这是第一种布局")
            Constant.constant.BuglyID ->
                holder.setImageResource(R.id.img_news_photo, R.mipmap.icon_home)
            else -> holder.setText(R.id.item_search, "这是第0种布局")
        }
    }

}
