package com.wangbin.mydome.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.wangbin.mydome.tools.GlideImageLoader
import java.io.File

/**
 * @ClassName BaseViewHolder
 * @Description RecyclerView的BaseViewHolder
 * @Author WangBin
 * @Date 2019/2/20 17:59
 */
class BaseViewHolder(private val mContext: Context, private val mConvertView: View) : RecyclerView.ViewHolder(mConvertView) {
    private val mViews: SparseArray<View> = SparseArray() //用来存储控件

    /**
     * 获取控件
     */
    private fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return (view as T?)!!
    }


    /**
     * 给TextView设置setText方法
     */
    fun setText(viewId: Int, text: String): BaseViewHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    /**
     * 给TextView设置setText方法
     */
    fun setEdit(viewId: Int, text: String): BaseViewHolder {
        val tv = getView<EditText>(viewId)
        tv.setText(text)
        return this
    }

    /**
     * 给ImageView设置setImageResource方法
     */
    fun setImageResource(viewId: Int, resId: Int): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    /**
     * 给ImageView设置路径图片方法
     */
    fun setImageResource(viewId: Int, res: String): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        GlideImageLoader.loadImage(mContext, res, view)
        return this
    }

    /**
     * 给ImageView设置文件图片方法
     */
    fun setImageResource(viewId: Int, file: File): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        GlideImageLoader.loadImage(mContext, file, view)
        return this
    }

    /**
     * 添加点击事件
     */
    fun setOnClickListener(viewId: Int, listener: View.OnClickListener): BaseViewHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    companion object {
        /**
         * 提供一个获取ViewHolder的方法
         */
        fun getRecyclerHolder(context: Context, parent: ViewGroup, layoutId: Int): BaseViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return BaseViewHolder(context, itemView)
        }
    }
}
