package com.wangbin.mydome.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by wangbin
 * on 2018/7/16.
 */
class ViewPagerSlide : ViewPager {
    //是否可以进行滑动
    private var isSlide = false

    fun setSlide(slide: Boolean) {
        isSlide = slide
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isSlide
    }
}
