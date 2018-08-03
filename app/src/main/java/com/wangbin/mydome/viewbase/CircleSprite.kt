package com.wangbin.mydome.viewbase

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import com.wangbin.mydome.viewbase.ShapeSprite

/**
 * Created by WangBin
 * on 2018/8/8.
 */

open class CircleSprite : ShapeSprite() {
    override fun getAnimation(): ValueAnimator? {
        return null
    }

    override fun drawShape(canvas: Canvas, paint: Paint) {
        if (getDrawBounds() != null) {
            val radius = Math.min(getDrawBounds().width(), getDrawBounds().height()) / 2
            canvas.drawCircle(getDrawBounds().centerX().toFloat(),
                    getDrawBounds().centerY().toFloat(),
                    radius.toFloat(), paint)
        }
    }
}
