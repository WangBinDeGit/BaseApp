package com.wangbin.mydome.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wangbin.mydome.R
import java.util.*

/**
 * Created by jxd.
 * on 2018/11/2
 */
internal class NewsAdapter(private val mContext: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var mDatas: List<String> = ArrayList()
    private var mHeaderView: View? = null
    private var mFooterView: View? = null
    private var mEmptyView: View? = null

    private val ITEM_TYPE_NORMAL = 0
    private val ITEM_TYPE_HEADER = 1
    private val ITEM_TYPE_FOOTER = 2
    private val ITEM_TYPE_EMPTY = 3

    fun setDatas(datas: List<String>) {
        mDatas = datas
        notifyDataSetChanged()
    }

    // 创建视图
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> ViewHolder(mHeaderView!!)
            ITEM_TYPE_EMPTY -> ViewHolder(mEmptyView!!)
            ITEM_TYPE_FOOTER -> ViewHolder(mFooterView!!)
            else -> {
                val v = LayoutInflater.from(mContext).inflate(R.layout.layout_recyclerview_item_view, parent, false)
                ViewHolder(v)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (null != mHeaderView && position == 0) {
            return ITEM_TYPE_HEADER
        }
        if (null != mFooterView && position == itemCount - 1) {
            return ITEM_TYPE_FOOTER
        }
        return if (null != mEmptyView && mDatas.size == 0) {
            ITEM_TYPE_EMPTY
        } else ITEM_TYPE_NORMAL

    }

    // 为Item绑定数据
    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == ITEM_TYPE_HEADER
                || type == ITEM_TYPE_FOOTER
                || type == ITEM_TYPE_EMPTY) {
            return
        }
        val realPos = getRealItemPosition(position)
        holder.mTxtTitle!!.text = mDatas[realPos]
    }

    private fun getRealItemPosition(position: Int): Int {
        return if (null != mHeaderView) {
            position - 1
        } else position
    }

    override fun getItemCount(): Int {
        var itemCount = mDatas.size
        if (null != mEmptyView && itemCount == 0) {
            itemCount++
        }
        if (null != mHeaderView) {
            itemCount++
        }
        if (null != mFooterView) {
            itemCount++
        }
        return itemCount
    }

    fun addHeaderView(view: View) {
        mHeaderView = view
        notifyItemInserted(0)
    }

    fun addFooterView(view: View) {
        mFooterView = view
        notifyItemInserted(itemCount - 1)
    }

    fun setEmptyView(view: View) {
        mEmptyView = view
        notifyDataSetChanged()
    }

    internal inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var mTxtTitle: TextView? = null
        var mTxtContent: TextView? = null
        var mImgPhoto: ImageView? = null

        init {
            mTxtTitle = v.findViewById(R.id.txt_news_title)
            mTxtContent = v.findViewById(R.id.txt_news_content)
            mImgPhoto = v.findViewById(R.id.img_news_photo)
        }
    }
}