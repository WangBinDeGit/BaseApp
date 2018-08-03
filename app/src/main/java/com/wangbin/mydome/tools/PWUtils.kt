package com.wangbin.mydome.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.wangbin.mydome.viewbase.PickerScrollView
import com.wangbin.mydome.R
import com.wangbin.mydome.bean.ConfigPropertyModel
import com.wangbin.mydome.bean.SingleTitleModel
import java.util.*

/**
 * Created by WangBin on 2018/4/7.
 * PWUtils
 */
class PWUtils() {
    var item: ConfigPropertyModel? = null

    var itemStr: SingleTitleModel? = null
    internal var isFirst = true
    fun showSelectPW(context: Context, window: Window, rootview: View, textView: TextView, datas: ArrayList<ConfigPropertyModel>) {
        isFirst = true
        val text_cancle: TextView
        val text_ok: TextView
        val pickerscrlllview: PickerScrollView<ConfigPropertyModel>
        val view = LayoutInflater.from(context).inflate(R.layout.popuwindow_select, null)
        text_cancle = view.findViewById(R.id.text_cancle)
        text_ok = view.findViewById(R.id.text_ok)
        pickerscrlllview = view.findViewById<PickerScrollView<ConfigPropertyModel>>(R.id.pickerscrlllview) as PickerScrollView<ConfigPropertyModel>
        pickerscrlllview.setData(datas)
        pickerscrlllview.setSelected(0)
        text_cancle.setOnClickListener { pw!!.dismiss() }

        text_ok.setOnClickListener {
            pw!!.dismiss()
            if (isFirst) {
                textView.text = if (datas.size > 0) datas[pickerscrlllview.getmCurrentSelected()].title else ""
            } else {
                textView.text = item!!.title
            }
        }

        pickerscrlllview.setOnSelectListener(object : PickerScrollView.onSelectListener<ConfigPropertyModel> {
            override fun onSelect(pickers: ConfigPropertyModel) {
                isFirst = false
                item = pickers
            }
        })
        // 创建PopupWindow实例
        pw = PopupWindow(view)
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        pw!!.animationStyle = R.style.mypopwindow_anim_style
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        pw!!.isFocusable = true
        pw!!.setBackgroundDrawable(BitmapDrawable())
        pw!!.isOutsideTouchable = true
        pw!!.width = LinearLayout.LayoutParams.MATCH_PARENT
        pw!!.height = LinearLayout.LayoutParams.WRAP_CONTENT
        // 在底部显示
        pw!!.showAtLocation(rootview, Gravity.BOTTOM, 0, 0)
        val lp = window.attributes
        lp.alpha = 0.6f
        window.attributes = lp
        pw!!.setOnDismissListener {
            val attributes = window.attributes
            attributes.alpha = 1f
            window.attributes = attributes
        }
    }

    fun showSelectPW(context: Activity, window: Window, rootview: View, onItemChoose: OnItemChooseListener<ConfigPropertyModel>, datas: ArrayList<ConfigPropertyModel>) {
        if (context.currentFocus != null) {
            val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
        isFirst = true
        val text_cancle: TextView
        val text_ok: TextView
        val pickerscrlllview: PickerScrollView<ConfigPropertyModel>
        val view = LayoutInflater.from(context).inflate(R.layout.popuwindow_select, null)
        text_cancle = view.findViewById(R.id.text_cancle)
        text_ok = view.findViewById(R.id.text_ok)
        pickerscrlllview = view.findViewById(R.id.pickerscrlllview)
        pickerscrlllview.setData(datas)
        pickerscrlllview.setSelected(0)
        text_cancle.setOnClickListener { pw!!.dismiss() }

        text_ok.setOnClickListener {
            pw!!.dismiss()
            if (isFirst) {
                onItemChoose.onChooseItem(datas[pickerscrlllview.getmCurrentSelected()])
            } else {
                onItemChoose.onChooseItem(item!!)
            }
        }

        pickerscrlllview.setOnSelectListener(object : PickerScrollView.onSelectListener<ConfigPropertyModel> {
            override fun onSelect(pickers: ConfigPropertyModel) {
                isFirst = false
                item = pickers
            }
        })
        // 创建PopupWindow实例
        pw = PopupWindow(view)
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        pw!!.animationStyle = R.style.mypopwindow_anim_style
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        pw!!.isFocusable = true
        pw!!.setBackgroundDrawable(BitmapDrawable())
        pw!!.isOutsideTouchable = true
        pw!!.width = LinearLayout.LayoutParams.MATCH_PARENT
        pw!!.height = LinearLayout.LayoutParams.WRAP_CONTENT
        // 在底部显示
        pw!!.showAtLocation(rootview, Gravity.BOTTOM, 0, 0)
        val lp = window.attributes
        lp.alpha = 0.6f
        window.attributes = lp
        pw!!.setOnDismissListener {
            val attributes = window.attributes
            attributes.alpha = 1f
            window.attributes = attributes
        }
    }


    @SuppressLint("NewApi")
    fun showSelectStrPW(context: Activity, window: Window, rootview: View, onItemChoose: OnItemChooseListener<SingleTitleModel>, datas: ArrayList<SingleTitleModel>) {
        if (context.currentFocus != null) {
            val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
        isFirst = true
        val text_cancle: TextView
        val text_ok: TextView
        val pickerscrlllview: PickerScrollView<SingleTitleModel>
        val view = LayoutInflater.from(context).inflate(R.layout.popuwindow_select, null)
        text_cancle = view.findViewById(R.id.text_cancle)
        text_ok = view.findViewById(R.id.text_ok)
        pickerscrlllview = view.findViewById<PickerScrollView<SingleTitleModel>>(R.id.pickerscrlllview)
        pickerscrlllview.setData(datas)
        pickerscrlllview.setSelected(0)
        text_cancle.setOnClickListener { pw!!.dismiss() }

        text_ok.setOnClickListener {
            pw!!.dismiss()
            if (isFirst) {
                onItemChoose.onChooseItem(datas[pickerscrlllview.getmCurrentSelected()])
            } else {
                onItemChoose.onChooseItem(itemStr!!)
            }
        }

        pickerscrlllview.setOnSelectListener(object : PickerScrollView.onSelectListener<SingleTitleModel> {
            override fun onSelect(pickers: SingleTitleModel) {
                isFirst = false
                itemStr = pickers
            }
        })
        // 创建PopupWindow实例
        pw = PopupWindow(view)
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        pw!!.animationStyle = R.style.mypopwindow_anim_style
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        pw!!.isFocusable = true
        pw!!.setBackgroundDrawable(BitmapDrawable())
        pw!!.isOutsideTouchable = true
        pw!!.width = LinearLayout.LayoutParams.MATCH_PARENT
        pw!!.height = LinearLayout.LayoutParams.WRAP_CONTENT
        // 在底部显示
        pw!!.showAtLocation(rootview, Gravity.BOTTOM, 0, 0)
        val lp = window.attributes
        lp.alpha = 0.6f
        window.attributes = lp
        pw!!.setOnDismissListener {
            val attributes = window.attributes
            attributes.alpha = 1f
            window.attributes = attributes
        }
    }

    fun showSelectStrPW(context: Activity, window: Window, rootview: View, textView: TextView, datas: ArrayList<SingleTitleModel>) {
        if (context.currentFocus != null) {
            val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
        isFirst = true
        val text_cancle: TextView
        val text_ok: TextView
        val pickerscrlllview: PickerScrollView<SingleTitleModel>
        val view = LayoutInflater.from(context).inflate(R.layout.popuwindow_select, null)
        text_cancle = view.findViewById(R.id.text_cancle)
        text_ok = view.findViewById(R.id.text_ok)
        pickerscrlllview = view.findViewById<PickerScrollView<SingleTitleModel>>(R.id.pickerscrlllview) as PickerScrollView<SingleTitleModel>
        pickerscrlllview.setData(datas)
        pickerscrlllview.setSelected(0)
        text_cancle.setOnClickListener { pw!!.dismiss() }

        text_ok.setOnClickListener {
            pw!!.dismiss()
            if (isFirst) {
                textView.text = if (datas.size > 0) datas[pickerscrlllview.getmCurrentSelected()].title else ""
            } else {
                textView.text = itemStr!!.title
            }
        }

        pickerscrlllview.setOnSelectListener(object : PickerScrollView.onSelectListener<SingleTitleModel> {
            override fun onSelect(pickers: SingleTitleModel) {
                isFirst = false
                itemStr = pickers
            }
        })
        // 创建PopupWindow实例
        pw = PopupWindow(view)
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        pw!!.animationStyle = R.style.mypopwindow_anim_style
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        pw!!.isFocusable = true
        pw!!.setBackgroundDrawable(BitmapDrawable())
        pw!!.isOutsideTouchable = true
        pw!!.width = LinearLayout.LayoutParams.MATCH_PARENT
        pw!!.height = LinearLayout.LayoutParams.WRAP_CONTENT
        // 在底部显示
        pw!!.showAtLocation(rootview, Gravity.BOTTOM, 0, 0)
        val lp = window.attributes
        lp.alpha = 0.6f
        window.attributes = lp
        pw!!.setOnDismissListener {
            val attributes = window.attributes
            attributes.alpha = 1f
            window.attributes = attributes
        }
    }

    private object Holder {
        val INSTANCE = PWUtils()
    }

    companion object {
        val instancePW: PWUtils by lazy { Holder.INSTANCE }
        var pw: PopupWindow? = null
    }

    interface OnItemChooseListener<T> {
        fun onChooseItem(item: T)
    }
}