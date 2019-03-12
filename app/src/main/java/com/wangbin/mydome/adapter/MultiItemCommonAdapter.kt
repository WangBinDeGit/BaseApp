package com.wangbin.mydome.adapter

import android.content.Context
import android.view.ViewGroup

import com.wangbin.mydome.base.BaseViewHolder
import com.wangbin.mydome.interfaces.ConmonItemType

/**
 * @ClassName MultiItemCommonAdapter
 * @Description TODO
 * @Author WangBin
 * @Date 2019/2/21 16:25
 */

abstract class MultiItemCommonAdapter<T>(private val mContext: Context, private val mDatas: List<T>) : BaseRecyclerAdapter<T>(mDatas) {

    abstract fun addConmonItemType(): ConmonItemType<T>

    override fun getItemViewType(position: Int): Int {
        return addConmonItemType().getItemViewType(position, mDatas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.getRecyclerHolder(mContext, parent, addConmonItemType().getLayoutId(viewType))
    }

}