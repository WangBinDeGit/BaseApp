package com.wangbin.mydome.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation

import com.wangbin.mydome.R


class ClearEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = android.R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyle), OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null
    private val iconDrawable: Drawable? = null

    init {
        init()
    }

    private fun init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            mClearDrawable = resources
                    .getDrawable(R.mipmap.icon_close)
        }
        mClearDrawable!!.setBounds(0, 0, mClearDrawable!!.intrinsicWidth, mClearDrawable!!.intrinsicHeight)
        setClearIconVisible(false)
        onFocusChangeListener = this
        addTextChangedListener(this)
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                val touchable = event.x > (width
                        - paddingRight - mClearDrawable!!.intrinsicWidth) && event.x < width - paddingRight
                if (touchable) {
                    this.setText("")
                }
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisible(text.length > 0)
        } else {
            setClearIconVisible(false)
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0],
                compoundDrawables[1], right, compoundDrawables[3])
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    override fun onTextChanged(s: CharSequence, start: Int, count: Int,
                               after: Int) {
        setClearIconVisible(s.length > 0)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                   after: Int) {

    }

    override fun afterTextChanged(s: Editable) {

    }

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        this.animation = shakeAnimation(5)
    }

    companion object {
        /**
         * 晃动动画
         * @param counts 1秒钟晃动多少下
         * @return
         */
        fun shakeAnimation(counts: Int): Animation {
            val translateAnimation = TranslateAnimation(0f, 10f, 0f, 0f)
            translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
            translateAnimation.duration = 1000
            return translateAnimation
        }
    }
}

