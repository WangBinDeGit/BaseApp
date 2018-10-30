package com.wangbin.mydome.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView
import android.widget.ListAdapter

/**
 * Created by wangbin
 * on 2017/3/6.
 */

class MyGridView : GridView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun setAdapter(adapter: ListAdapter) {
        super.setAdapter(adapter)
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
