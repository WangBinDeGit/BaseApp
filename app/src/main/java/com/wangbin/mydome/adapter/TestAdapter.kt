package com.wangbin.mydome.adapter

import android.content.Context
import android.view.ViewGroup
import com.wangbin.mydome.R
import com.wangbin.mydome.base.BaseViewHolder

/**
 * @ClassName TestAdapter
 * @Description TODO
 * @Author WangBin
 * @Date 2019/2/20 19:27
 */
class TestAdapter(private val mContext: Context, data: List<String>) : BaseRecyclerAdapter<String>(data) {

    override fun onBindData(holder: BaseViewHolder, bean: String, position: Int) {
        holder.setText(R.id.item_search, bean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.getRecyclerHolder(mContext, parent, R.layout.item_search)
    }

}