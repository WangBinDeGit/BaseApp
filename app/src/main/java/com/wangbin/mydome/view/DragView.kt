package com.wangbin.mydome.view

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout


@Suppress("DEPRECATED_IDENTITY_EQUALS")
/**
 * 侧滑
 */
class DragView : RelativeLayout,View.OnClickListener {
    private var fgView: View? = null
    var bgView: View? = null
    private var mDrager: ViewDragHelper? = null
    private var mDragStateListener: DragStateListener? = null
    private val DRAG_LEFT = -1
    private val DRAG_RIGHT = 1
    private var dragMode = DRAG_LEFT
    private var minX: Float = 0f
    private var maxX: Float = 0f
    override fun onClick(v: View?) {
        if(mDragStateListener != null) {
            if(v == fgView){
                if(isOpen()){
                    closeAnim();
                    return;
                }
                mDragStateListener!!.onForegroundViewClick(this , v!!);
            }else {
                mDragStateListener!!.onBackgroundViewClick(this , v!!);
            }
        }
    }

    constructor (context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mDrager = ViewDragHelper.create(this, 5f, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child === fgView
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return bgView!!.measuredWidth
            }


            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return getPositionX(left.toFloat())
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return 0
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                if (Math.abs(fgView!!.left) !== 0 || Math.abs(fgView!!.left) !== bgView!!.getMeasuredWidth()) {
                    val x = fgView!!.getLeft() + 0.1f * xvel
                    mDrager!!.smoothSlideViewTo(fgView!!,
                            if (Math.abs(getPositionX(x)) > bgView!!.getMeasuredWidth() / 2) bgView!!.getMeasuredWidth() * dragMode else 0, 0)
                    postInvalidate()
                }

            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                if (changedView === fgView)
                    parent.requestDisallowInterceptTouchEvent(fgView!!.left !== 0)
                if (mDragStateListener != null) {
                    if (left == 0) {
                        mDragStateListener!!.onClosed(this@DragView)
                    } else if (Math.abs(left) == bgView!!.measuredWidth) {
                        mDragStateListener!!.onOpened(this@DragView)
                    }
                }
            }
        })
    }

    fun getForegroundView(): View {
        return fgView!!
    }

    fun getBackgroundView(): View {
        return bgView!!
    }

    fun open() {
        fgView!!.offsetLeftAndRight(dragMode * (bgView!!.getMeasuredWidth() - fgView!!.getLeft()))
    }

    fun close() {
        fgView!!.offsetLeftAndRight(-fgView!!.getLeft())
    }

    fun openAnim() {
        mDrager!!.smoothSlideViewTo(fgView!!, bgView!!.getMeasuredWidth() * dragMode, 0)
        postInvalidate()
    }

    fun closeAnim() {
        mDrager!!.smoothSlideViewTo(fgView!!, 0, 0)
        postInvalidate()
    }

    fun isOpen(): Boolean {
        return Math.abs(fgView!!.getLeft()) === bgView!!.measuredWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //drag range
        if (dragMode === DRAG_LEFT) {
            minX = -bgView!!.measuredWidth.toFloat()
            maxX = 0f
        } else {
            minX = 0f
            maxX = bgView!!.measuredWidth.toFloat()
        }
    }

    private fun getPositionX(x: Float): Int {
        var x = x
        if (x < minX) x = minX
        if (x > maxX) x = maxX
        return x.toInt()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 2)
            throw IllegalArgumentException("must contain only two child view")
        fgView = getChildAt(1)
        bgView = getChildAt(0)
        if (!(fgView is ViewGroup && bgView is ViewGroup))
            throw IllegalArgumentException("ForegroundView and BackgoundView must be a subClass of ViewGroup")
        val param = bgView!!.getLayoutParams() as RelativeLayout.LayoutParams
        param.addRule(if (dragMode === DRAG_LEFT) RelativeLayout.ALIGN_PARENT_RIGHT else RelativeLayout.ALIGN_PARENT_LEFT)
        param.width = RelativeLayout.LayoutParams.WRAP_CONTENT

        //bind onClick Event
        fgView!!.setOnClickListener(this)
        val bgViewCount = (bgView as ViewGroup).childCount
        for (i in 0 until bgViewCount) {
            val child = (bgView as ViewGroup).getChildAt(i)
            if (child.isClickable) child.setOnClickListener(this)
        }
    }


    override fun computeScroll() {
        if (mDrager!!.continueSettling(true)) {
            postInvalidate()
        }
    }

    fun setOnDragStateListener(listener: DragStateListener) {
        mDragStateListener = listener
    }

    interface DragStateListener {
        fun onOpened(dragView: DragView)
        fun onClosed(dragView: DragView)
        fun onForegroundViewClick(dragView: DragView, v: View)
        fun onBackgroundViewClick(dragView: DragView, v: View)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDrager!!.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDrager!!.processTouchEvent(event)
        return true
    }


}