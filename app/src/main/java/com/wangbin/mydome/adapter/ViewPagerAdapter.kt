package com.wangbin.mydome.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by wangbin
 * on 2018/7/16.
 */

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var list: List<Fragment>? = null

    fun setList(list: List<Fragment>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return list!![position]
    }

    override fun getCount(): Int {
        return if (list != null) list!!.size else 0
    }
}