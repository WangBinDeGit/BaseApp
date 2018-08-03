package com.wangbin.mydome.viewbase;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Property;

import com.wangbin.mydome.tools.AnimationUtils;


/**
 * Created by ybq.
 */
@TargetApi(Build.VERSION_CODES.N)
public abstract class Sprite extends Drawable implements
        ValueAnimator.AnimatorUpdateListener
        , Animatable
        , Drawable.Callback {

    private float scale = 1;
    private float scaleX = 1;
    private float scaleY = 1;
    private float pivotX;
    private float pivotY;
    private int animationDelay;
    private int rotateX;
    private int rotateY;
    private int translateX;
    private int translateY;
    private int rotate;
    private float translateXPercentage;
    private float translateYPercentage;
    private ValueAnimator animator;
    private int alpha = 255;
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    private Rect drawBounds = ZERO_BOUNDS_RECT;
    private Camera mCamera;
    private Matrix mMatrix;

    Sprite() {
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    public abstract int getColor();

    public abstract void setColor(int color);

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public int getAlpha() {
        return alpha;
    }

    @SuppressLint("WrongConstant")
    @Override
    public int getOpacity() {
        return PixelFormat.RGBA_8888;
    }

    private float getTranslateXPercentage() {
        return translateXPercentage;
    }

    private void setTranslateXPercentage(float translateXPercentage) {
        this.translateXPercentage = translateXPercentage;
    }

    private float getTranslateYPercentage() {
        return translateYPercentage;
    }

    private void setTranslateYPercentage(float translateYPercentage) {
        this.translateYPercentage = translateYPercentage;
    }

    private int getTranslateX() {
        return translateX;
    }

    private void setTranslateX(int translateX) {
        this.translateX = translateX;
    }

    private int getTranslateY() {
        return translateY;
    }

    private void setTranslateY(int translateY) {
        this.translateY = translateY;
    }

    private int getRotate() {
        return rotate;
    }

    private void setRotate(int rotate) {
        this.rotate = rotate;
    }

    private float getScale() {
        return scale;
    }

    private void setScale(float scale) {
        this.scale = scale;
        setScaleX(scale);
        setScaleY(scale);
    }

    private float getScaleX() {
        return scaleX;
    }

    private void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    private float getScaleY() {
        return scaleY;
    }

    private void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    private int getRotateX() {
        return rotateX;
    }

    private void setRotateX(int rotateX) {
        this.rotateX = rotateX;
    }

    private int getRotateY() {
        return rotateY;
    }

    private void setRotateY(int rotateY) {
        this.rotateY = rotateY;
    }

    private float getPivotX() {
        return pivotX;
    }

    private void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    private float getPivotY() {
        return pivotY;
    }

    private void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    @SuppressWarnings("unused")
    public int getAnimationDelay() {
        return animationDelay;
    }

    Sprite setAnimationDelay(int animationDelay) {
        this.animationDelay = animationDelay;
        return this;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    public abstract ValueAnimator getAnimation();

    @Override
    public void start() {
        animator = obtainAnimation();
        if (animator == null) {
            return;
        }
        if (animator.isStarted()) {
            return;
        }

        AnimationUtils.start(animator);
        invalidateSelf();
    }

    private ValueAnimator obtainAnimation() {
        if (animator == null) {
            animator = getAnimation();
        }
        if (animator != null) {
            animator.setStartDelay(animationDelay);
            animator.addUpdateListener(this);
        }
        return animator;
    }

    @Override
    public void stop() {
        if (animator != null) {
            animator.end();
            reset();
            onAnimationUpdate(animator);
        }
    }


    protected abstract void drawSelf(Canvas canvas);

    private void reset() {
        scale = 1;
        rotateX = 0;
        rotateY = 0;
        translateX = 0;
        translateY = 0;
        rotate = 0;
        translateXPercentage = 0f;
        translateYPercentage = 0f;
    }

    @Override
    public boolean isRunning() {
        return AnimationUtils.isRunning(animator);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        setDrawBounds(bounds);
    }

    private void setDrawBounds(Rect drawBounds) {
        setDrawBounds(drawBounds.left, drawBounds.top, drawBounds.right, drawBounds.bottom);
    }

    void setDrawBounds(int left, int top, int right, int bottom) {
        this.drawBounds = new Rect(left, top, right, bottom);
        setPivotX(getDrawBounds().centerX());
        setPivotY(getDrawBounds().centerY());
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        invalidateSelf();
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }


    public Rect getDrawBounds() {
        return drawBounds;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        int tx = getTranslateX();
        tx = tx == 0 ? (int) (getBounds().width() * getTranslateXPercentage()) : tx;
        int ty = getTranslateY();
        ty = ty == 0 ? (int) (getBounds().height() * getTranslateYPercentage()) : ty;
        canvas.translate(tx,
                ty);
        canvas.scale(getScaleX(), getScaleY(), getPivotX(), getPivotY());
        canvas.rotate(getRotate(), getPivotX(), getPivotY());

        if (getRotateX() != 0 || getRotateY() != 0) {
            mCamera.save();
            mCamera.rotateX(getRotateX());
            mCamera.rotateY(getRotateY());
            mCamera.getMatrix(mMatrix);
            mMatrix.preTranslate(-getPivotX(), -getPivotY());
            mMatrix.postTranslate(getPivotX(), getPivotY());
            mCamera.restore();
            canvas.concat(mMatrix);
        }
        drawSelf(canvas);
    }

    Rect clipSquare(Rect rect) {
        int w = rect.width();
        int h = rect.height();
        int min = Math.min(w, h);
        int cx = rect.centerX();
        int cy = rect.centerY();
        int r = min / 2;
        return new Rect(
                cx - r,
                cy - r,
                cx + r,
                cy + r
        );
    }

    static final Property<Sprite, Integer> ROTATE_X = new IntProperty<Sprite>("rotateX") {
        @Override
        public void setValue(Sprite object, int value) {
            object.setRotateX(value);
        }

        @Override
        public Integer get(Sprite object) {
            return object.getRotateX();
        }
    };

    static final Property<Sprite, Integer> ROTATE = new IntProperty<Sprite>("rotate") {
        @Override
        public void setValue(Sprite object, int value) {
            object.setRotate(value);
        }

        @Override
        public Integer get(Sprite object) {
            return object.getRotate();
        }
    };

    static final Property<Sprite, Integer> ROTATE_Y = new IntProperty<Sprite>("rotateY") {
        @Override
        public void setValue(Sprite object, int value) {
            object.setRotateY(value);
        }

        @Override
        public Integer get(Sprite object) {
            return object.getRotateY();
        }
    };

    @SuppressWarnings("unused")
    static final Property<Sprite, Integer> TRANSLATE_X = new IntProperty<Sprite>("translateX") {
        @Override
        public void setValue(Sprite object, int value) {
            object.setTranslateX(value);
        }

        @Override
        public Integer get(Sprite object) {
            return object.getTranslateX();
        }
    };
    @SuppressWarnings("unused")
    static final Property<Sprite, Integer> TRANSLATE_Y = new IntProperty<Sprite>("translateY") {
        @Override
        public void setValue(Sprite object, int value) {
            object.setTranslateY(value);
        }

        @Override
        public Integer get(Sprite object) {
            return object.getTranslateY();
        }
    };


    static final Property<Sprite, Float> TRANSLATE_X_PERCENTAGE = new FloatProperty<Sprite>("translateXPercentage") {
        @Override
        public void setValue(Sprite object, float value) {
            object.setTranslateXPercentage(value);
        }

        @Override
        public Float get(Sprite object) {
            return object.getTranslateXPercentage();
        }
    };


    static final Property<Sprite, Float> TRANSLATE_Y_PERCENTAGE = new FloatProperty<Sprite>("translateYPercentage") {
        @Override
        public void setValue(Sprite object, float value) {
            object.setTranslateYPercentage(value);
        }

        @Override
        public Float get(Sprite object) {
            return object.getTranslateYPercentage();
        }
    };
    @SuppressWarnings("unused")
    static final Property<Sprite, Float> SCALE_X = new FloatProperty<Sprite>("scaleX") {
        @Override
        public void setValue(Sprite object, float value) {
            object.setScaleX(value);
        }

        @Override
        public Float get(Sprite object) {
            return object.getScaleX();
        }
    };
    static final Property<Sprite, Float> SCALE_Y = new FloatProperty<Sprite>("scaleY") {
        @Override
        public void setValue(Sprite object, float value) {
            object.setScaleY(value);
        }

        @Override
        public Float get(Sprite object) {
            return object.getScaleY();
        }
    };

    static final Property<Sprite, Float> SCALE = new FloatProperty<Sprite>("scale") {
        @Override
        public void setValue(Sprite object, float value) {
            object.setScale(value);
        }

        @Override
        public Float get(Sprite object) {
            return object.getScale();
        }
    };
    static final Property<Sprite, Integer> ALPHA = new IntProperty<Sprite>("alpha") {
        @Override
        public void setValue(Sprite object, int value) {
            object.setAlpha(value);
        }

        @Override
        public Integer get(Sprite object) {
            return object.getAlpha();
        }
    };


}
