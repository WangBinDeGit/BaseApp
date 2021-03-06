package com.wangbin.mydome.viewbase

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * Created by WangBin on 2018/4/17.
 * 解决viewpager切换经过中间界面的问题
 */

class ViewPagerScroller : Scroller {

    private var mScrollDuration = 0             // 滑动速度搜索

    /**
     * 设置速度速度
     * @param duration
     */
    fun setScrollDuration(duration: Int) {
        this.mScrollDuration = duration
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, interpolator: Interpolator) : super(context, interpolator) {}

    @SuppressLint("NewApi")
    constructor(context: Context, interpolator: Interpolator, flywheel: Boolean) : super(context, interpolator, flywheel) {
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration)
    }

    fun initViewPagerScroll(viewPager: ViewPager) {
        try {
            val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            mScroller.set(viewPager, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}