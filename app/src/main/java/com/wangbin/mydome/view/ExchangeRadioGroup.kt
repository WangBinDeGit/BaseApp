package com.wangbin.mydome.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.wangbin.mydome.R

class ExchangeRadioGroup : RadioGroup {
    private val TAG = "RadioGroupEx"
    val DEFULT_ROW_NUM = 3
    private var ROW_NUM = DEFULT_ROW_NUM//列数

    constructor(context: Context?) : super(context)

    @SuppressLint("Recycle")
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.ExchangeRadioGroup)

        ROW_NUM = a.getInteger(R.styleable.ExchangeRadioGroup_row_num, DEFULT_ROW_NUM)

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        //调用ViewGroup的方法，测量子view
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        //最大的宽
        var maxWidth = 0
        //累计的高
        var totalHeight = 0

        //当前这一行的累计行宽
        var lineWidth = 0
        //当前这行的最大行高
        var maxLineHeight = 0
        //用于记录换行前的行宽和行高
        var oldHeight: Int
        var oldWidth: Int

        val count = childCount
        //假设 widthMode和heightMode都是AT_MOST
        for (i in 0 until count) {
            val child = getChildAt(i)
            val params = child.layoutParams as ViewGroup.MarginLayoutParams
            //得到这一行的最高
            oldHeight = maxLineHeight
            //当前最大宽度
            oldWidth = maxWidth

            val deltaX = child.measuredWidth + params.leftMargin + params.rightMargin
            if (lineWidth + deltaX + paddingLeft + paddingRight > widthSize) {//如果折行,height增加
                //和目前最大的宽度比较,得到最宽。不能加上当前的child的宽,所以用的是oldWidth
                maxWidth = Math.max(lineWidth, oldWidth)
                //重置宽度
                lineWidth = deltaX
                //累加高度
                totalHeight += oldHeight
                //重置行高,当前这个View，属于下一行，因此当前最大行高为这个child的高度加上margin
                maxLineHeight = child.measuredHeight + params.topMargin + params.bottomMargin
                Log.v(TAG, "maxHeight:$totalHeight---maxWidth:$maxWidth")

            } else {
                //不换行，累加宽度
                lineWidth += deltaX
                //不换行，计算行最高
                val deltaY = child.measuredHeight + params.topMargin + params.bottomMargin
                maxLineHeight = Math.max(maxLineHeight, deltaY)
            }
            if (i == count - 1) {
                //前面没有加上下一行的搞，如果是最后一行，还要再叠加上最后一行的最高的值
                totalHeight += maxLineHeight
                //计算最后一行和前面的最宽的一行比较
                maxWidth = Math.max(lineWidth, oldWidth)
            }
        }

        //加上当前容器的padding值
        maxWidth += paddingLeft + paddingRight
        totalHeight += paddingTop + paddingBottom
        setMeasuredDimension(if (widthMode == View.MeasureSpec.EXACTLY) widthSize else maxWidth,
                if (heightMode == View.MeasureSpec.EXACTLY) heightSize else totalHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        //pre为前面所有的child的相加后的位置
        var preLeft = paddingLeft
        var preTop = paddingTop
        //记录每一行的最高值
        var maxHeight = 0
        //流式布局
//        for (i in 0 until count) {
//            val child = getChildAt(i)
//            val params = child.layoutParams as ViewGroup.MarginLayoutParams
//            //r-l为当前容器的宽度。如果子view的累积宽度大于容器宽度，就换行。
//            if (preLeft + params.leftMargin + child.measuredWidth + params.rightMargin + paddingRight > r - l) {
//                //重置
//                preLeft = paddingLeft
//                //要选择child的height最大的作为设置
//                preTop += maxHeight
//                maxHeight = getChildAt(i).measuredHeight + params.topMargin + params.bottomMargin
//            } else { //不换行,计算最大高度
//                maxHeight = Math.max(maxHeight, child.measuredHeight + params.topMargin + params.bottomMargin)
//            }
//            //left坐标
//            val left = preLeft + params.leftMargin
//            //top坐标
//            val top = preTop + params.topMargin
//            val right = left + child.measuredWidth
//            val bottom = top + child.measuredHeight
//            //为子view布局
//            child.layout(left, top, right, bottom)
//            //计算布局结束后，preLeft的值
//            preLeft += params.leftMargin + child.measuredWidth + params.rightMargin

//        }


        //固定每行个数
        for (i in 0 until count) {
            val child = getChildAt(i)
            val params = child.layoutParams as ViewGroup.MarginLayoutParams
            val margin = ((r - l) - ROW_NUM * child.measuredWidth) / (ROW_NUM * 2)//
            if (i % ROW_NUM == 0) {
                //重置
                preLeft = paddingLeft
                //要选择child的height最大的作为设置
                preTop += maxHeight
                maxHeight = getChildAt(i).measuredHeight + params.topMargin + params.bottomMargin
            } else { //不换行,计算最大高度
                maxHeight = Math.max(maxHeight, child.measuredHeight + params.topMargin + params.bottomMargin)
            }
            val left = preLeft + margin
            //top坐标
            val top = preTop + params.topMargin
            val right = left + child.measuredWidth
            val bottom = top + child.measuredHeight

            child.layout(left, top, right, bottom)
            //计算布局结束后，preLeft的值
            preLeft += margin + child.measuredWidth + margin
        }


    }
}