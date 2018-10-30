package com.wangbin.mydome.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.wangbin.mydome.R
import com.wangbin.mydome.bean.SearchBean

/**
 * Created by  wangbin.
 * on 2018/7/17
 */

class SearchAdapter(private val mContext: Context, private val mDatas: List<SearchBean>?) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.search.text = mDatas!![position].name
        if (mDatas[position].isFlag) {
            holder.lin_search.setBackgroundColor(ContextCompat.getColor(mContext, R.color.selected_search))
        } else {
            holder.lin_search.setBackgroundColor(ContextCompat.getColor(mContext, R.color.drawback))
        }

        holder.lin_search.setOnClickListener { view ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(view, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }

    fun setOnItemClickLitener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val search: TextView
        val lin_search: LinearLayout

        init {
            search = itemView.findViewById(R.id.item_search)
            lin_search = itemView.findViewById(R.id.lin_search)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
