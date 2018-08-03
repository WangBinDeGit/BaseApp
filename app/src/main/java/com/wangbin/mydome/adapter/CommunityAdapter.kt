package com.wangbin.mydome.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wangbin.mydome.R
import com.wangbin.mydome.bean.CommunityModel

/**
 * Created by  wangbin.
 * on 2018/7/17
 */
class CommunityAdapter(mContext: Context, private val data: ArrayList<CommunityModel>) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>(), View.OnClickListener {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_follow_house, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onClick(v: View?) {
        mItemClickListener?.onItemClick(v!!.tag as Int)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.tvname!!.text = data[position].name
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var tvname: TextView? = null

        init {
            tvname = view.findViewById(R.id.tv_house)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}