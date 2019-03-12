package com.wangbin.mydome.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * RecyclerView adapter基类
 *
 *
 * 封装了数据集合以及ItemView的点击事件回调,同时暴露 [.onBindData]
 * 用于数据与view绑定
 *
 * @param <T> A data bean class that will be used by the adapter.
</T> *
 *
 * Created by DavidChen on 2018/5/30.
 */

internal abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder>(private val mData: List<T>?) : RecyclerView.Adapter<V>() {

    private var mListener: OnItemClickListener? = null
    private var mLongListener: OnItemLongClickListener? = null

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.itemView.tag = position
        val bean = mData!![position]
        holder.itemView.setOnClickListener { view ->
            if (mListener != null) {
                mListener!!.onItemClick(this@BaseRecyclerViewAdapter, view, view.tag as Int)
            }
        }
        holder.itemView.setOnLongClickListener { view ->
            if (mLongListener != null) {
                mLongListener!!.onItemLongClick(this@BaseRecyclerViewAdapter, view, view.tag as Int)
            }
            true
        }
        onBindData(holder, bean, position)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.mLongListener = onItemLongClickListener
    }

    /**
     * 数据绑定，由实现类实现
     *
     * @param holder   The reference of the all view within the item.
     * @param bean     The data bean related to the position.
     * @param position The position to bind data.
     */
    protected abstract fun onBindData(holder: V, bean: T, position: Int)

    /**
     * item点击监听器
     */
    interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param adapter  The Adapter where the click happened.
         * @param v        The view that was clicked.
         * @param position The position of the view in the adapter.
         */
        fun onItemClick(adapter: RecyclerView.Adapter<*>, v: View, position: Int)
    }

    /**
     * item长按监听器
     */
    interface OnItemLongClickListener {
        /**
         * item长按回调
         *
         * @param adapter  The Adapter where the click happened.
         * @param v        The view that was clicked.
         * @param position The position of the view in the adapter.
         */
        fun onItemLongClick(adapter: RecyclerView.Adapter<*>, v: View, position: Int)
    }
}
