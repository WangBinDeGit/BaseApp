package com.wangbin.mydome.viewbase;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.wangbin.mydome.tools.AnimationUtils;


/**
 * Created by ybq.
 */
public abstract class SpriteGroup extends Sprite {

    private Sprite[] sprites;

    private int color;

    SpriteGroup() {
        sprites = onCreateChild();
        initCallBack();
        onChildCreated();
    }

    private void initCallBack() {
        if (sprites != null) {
            for (Sprite sprite : sprites) {
                sprite.setCallback(this);
            }
        }
    }

    private void onChildCreated() {

    }

    int getChildCount() {
        return sprites == null ? 0 : sprites.length;
    }

    Sprite getChildAt(int index) {
        return sprites == null ? null : sprites[index];
    }


    @Override
    public void setColor(int color) {
        this.color = color;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setColor(color);
        }
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        drawChild(canvas);
    }

    public void drawChild(Canvas canvas) {
        if (sprites != null) {
            for (Sprite sprite : sprites) {
                int count = canvas.save();
                sprite.draw(canvas);
                canvas.restoreToCount(count);
            }
        }
    }


    @Override
    protected void drawSelf(Canvas canvas) {
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        for (Sprite sprite : sprites) {
            sprite.setBounds(bounds);
        }
    }


    @Override
    public void start() {
        super.start();
        AnimationUtils.start(sprites);
    }

    @Override
    public void stop() {
        super.stop();
        AnimationUtils.stop(sprites);
    }


    @Override
    public boolean isRunning() {
        return AnimationUtils.isRunning(sprites) ||super.isRunning();
    }

    public abstract Sprite[] onCreateChild();

    @Override
    public ValueAnimator getAnimation() {
        return null;
    }
}
