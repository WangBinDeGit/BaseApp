package com.wangbin.mydome.tools;

import android.content.Context;

/**
 * @ClassName DensityUtil
 * @Description 分辨率转换
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 传入Context
     * @param dpValue dp的值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 传入Context
     * @param pxValue px的值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp(像素) 的单位 转成为 px
     *
     * @param context 传入Context
     * @param spValue sp的值
     */
    public static float sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }
}
