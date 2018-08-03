package com.wangbin.mydome.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * Created by WangBin on 2018/4/16.
 * ObservableScrollView
 */

class ObservableScrollView : ScrollView, ObservableScrollable {
    private val mDisableEdgeEffects = true

    private var mOnScrollChangedListener: OnScrollChangedCallback? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle)

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener!!.onScroll(l, t)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun getTopFadingEdgeStrength(): Float {
        return if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            0.0f
        } else super.getTopFadingEdgeStrength()
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun getBottomFadingEdgeStrength(): Float {
        return if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            0.0f
        } else super.getBottomFadingEdgeStrength()

    }

    override fun setOnScrollChangedCallback(callback: OnScrollChangedCallback) {
        mOnScrollChangedListener = callback
    }

    override fun computeScrollDeltaToGetChildRectOnScreen(rect: Rect): Int {
        // TODO Auto-generated method stub
        // 禁止scrollView内布局变化后自动滚动
        return 0
    }
}