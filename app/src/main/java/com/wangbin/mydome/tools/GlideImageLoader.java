package com.wangbin.mydome.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wangbin.mydome.R;

import java.io.File;

/**
 * 1. 默认使用内存缓存和磁盘缓存,如果不适用内存缓存，使用skipMemoryCache API<br>
 * 2. diskCacheStrategy(DiskCacheStrategy.NONE)可以禁用緩存<br>
 * 3. 所有加載方式默認使用淡入的动画，暫不提供不适用动画的方法
 */

public class GlideImageLoader {

    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).crossFade().into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).crossFade().into(iv);
    }

    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).crossFade().into(iv);
    }


    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).transform(new GlideCircleTransform(context)).crossFade().into(iv);
    }

    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).transform(new GlideRoundCornerTransform(context, 10)).crossFade().into(iv);
    }


    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .crossFade().into(imageView);


    }

    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .crossFade().into(imageView);
    }
}