package com.wangbin.mydome.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wangbin.mydome.R;

import java.io.File;

/**
 * @ClassName GlideImageLoader
 * @Description 图片显示工具类
 * 1. 默认使用内存缓存和磁盘缓存,如果不适用内存缓存，使用skipMemoryCache API<br>
 * 2. diskCacheStrategy(DiskCacheStrategy.NONE)可以禁用緩存<br>
 * 3. 所有加載方式默認使用淡入的动画，暫不提供不适用动画的方法
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
public class GlideImageLoader {

    /**
     * 默认显示图片到ImageView
     * @param context   Context
     * @param url       图片的url
     * @param erroImg   加载失败显示的图片
     * @param emptyImg  加载过程中显示的图片
     * @param imageView 显示到的控件
     */
    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView imageView) {
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).crossFade().into(imageView);
    }

    /**
     * 默认显示图片到ImageView
     * @param context   Context
     * @param url       图片的url
     * @param imageView 显示到的控件
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).crossFade().into(imageView);
    }

    /**
     * 默认显示图片到ImageView
     * @param context   Context
     * @param file      本地文件图片
     * @param imageView 显示到的控件
     */
    public static void loadImage(Context context, File file, final ImageView imageView) {
        Glide.with(context).load(file).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).crossFade().into(imageView);
    }

    /**
     * 默认显示图片到ImageView
     * @param context       Context
     * @param resourceId    资源图片id
     * @param imageView     显示到的控件
     */
    public static void loadImage(Context context, int resourceId, final ImageView imageView) {
        Glide.with(context).load(resourceId).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).crossFade().into(imageView);
    }

    /**
     * 默认显示gif图片到ImageView
     * @param context       Context
     * @param url           图片url
     * @param iv            显示到的控件
     */
    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).crossFade().into(iv);
    }

    /**
     * 显示圆形图片到ImageView
     * @param context       Context
     * @param url           图片url
     * @param iv            显示到的控件
     */
    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).transform(new GlideCircleTransform(context)).crossFade().into(iv);
    }

    /**
     * 显示设置圆角图片到ImageView
     * @param context       Context
     * @param url           图片url
     * @param iv            显示到的控件
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv,int dpValue) {
        Glide.with(context).load(url).placeholder(R.mipmap.ic_empty).error(R.mipmap.ic_error).transform(new GlideRoundCornerTransform(context, dpValue)).crossFade().into(iv);
    }

}