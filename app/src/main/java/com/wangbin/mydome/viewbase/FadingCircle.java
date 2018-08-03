package com.wangbin.mydome.viewbase;

import android.animation.ValueAnimator;

/**
 * Created by WangBin.
 * on 2018/8/8.
 */
public class FadingCircle extends CircleSpriteGroup {

    @Override
    public Sprite[] onCreateChild() {
        Dot[] dots = new Dot[12];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new Dot();
            dots[i].setAnimationDelay(100 * i);
        }
        return dots;
    }

    class Dot extends CircleSprite {

        @Override
        public ValueAnimator getAnimation() {
            float fractions[] = new float[]{0f, 0.4f, 1f};
            return new SpriteAnimatorBuilder(this).
                    alpha(fractions, 0, 255, 0).
                    duration(1200).
                    easeInOut(fractions).build();
        }
    }
}
