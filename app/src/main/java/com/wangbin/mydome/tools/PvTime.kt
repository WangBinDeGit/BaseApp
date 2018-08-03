package com.wangbin.mydome.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.wangbin.mydome.tools.DateUtils.getDayTime
import com.wangbin.mydome.tools.DateUtils.getMonthTime
import com.wangbin.mydome.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by WangBin on 2018/4/7.
 * PvTime
 */
class PvTime() {
    private object Holder {
        val INSTANCE = PvTime()
    }

    companion object {
        val instancePV: PvTime by lazy { Holder.INSTANCE }
        @SuppressLint("StaticFieldLeak")
        var pvTime: TimePickerView? = null
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("SimpleDateFormat")
    fun setPvTime(context: Activity, tvTime: TextView, type: String) {
        if (context.currentFocus != null) {
            val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
        pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, _ ->
            //选中事件回调
            if (type == "bir") {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateff = dateFormat.format(Date())
                if (DateUtils.stringDayTimeChuo(dateff)!! - date!!.time > 0) {//时间大于当前时间
                    tvTime.text = getDayTime(date)
                } else {
                    Toast.makeText(context, "生日需小于当前时间", Toast.LENGTH_SHORT).show()
                }
            } else {
                tvTime.text = getDayTime(date)
            }
        })
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(R.color.textcolor6)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build()
        pvTime!!.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun setPvTime(context: Context, title: String, listener: OnTimeSelectListener) {
        pvTime = TimePickerBuilder(context, listener)
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(R.color.textcolor6)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build()
        pvTime!!.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun setAllPvTime(context: Context, tvTime: TextView, type: String) {
        pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, _ ->
            //选中事件回调
            if (type == "appoint") {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val dateff = dateFormat.format(Date())
                if (DateUtils.stringTimeChuo(dateff)!! - date!!.time < 0) {//时间大于当前时间
                    tvTime.text = getDayTime(date)
                } else {
                    Toast.makeText(context, "预约时间需大于当前时间", Toast.LENGTH_SHORT).show()
                }
            } else {
                tvTime.text = getDayTime(date)
            }
        })
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(R.color.textcolor6)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isDialog(true)
                .build()
        pvTime!!.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun setYMPvTime(context: Context, tvTime: TextView) {
        pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, _ ->
            //选中事件回调
            tvTime.text = getMonthTime(date)
//            context.searchData(getMonthTime(date))
        })
                .setType(booleanArrayOf(true, true, false, false, false, false))
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(R.color.textcolor6)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build()
        pvTime!!.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun setYMPvTimes(context: Context, tvTime: TextView) {
        pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, _ ->
            //选中事件回调
            tvTime.text = getMonthTime(date)
//            context.searchData(getMonthTime(date))
        })
                .setType(booleanArrayOf(true, true, false, false, false, false))
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(R.color.textcolor6)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build()
        pvTime!!.show()
    }
}