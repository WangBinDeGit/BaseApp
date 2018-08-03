package com.wangbin.mydome.interfaces

import android.os.Bundle
import android.view.View

/**
 * Created by  wangbin.
 * on 2018/8/2
 */
interface IViewSpecification {

    /**
     * 初始化Bundle参数
     */
    fun initParams(arguments: Bundle?)

    /**
     * 设置布局
     *
     * @return
     */
    fun intiLayout(): Int

    /**
     * 初始化控件
     */
    fun initView()

    /**
     * 初始化鉴定事件
     */
    fun setListener()

    /**
     * View点击
     */
    fun widgetClick( view: View)

}