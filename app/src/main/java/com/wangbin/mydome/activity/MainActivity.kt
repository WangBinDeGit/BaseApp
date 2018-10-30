package com.wangbin.mydome.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView

import com.wangbin.mydome.R
import com.wangbin.mydome.adapter.SearchAdapter
import com.wangbin.mydome.adapter.ViewPagerAdapter
import com.wangbin.mydome.bean.SearchBean
import com.wangbin.mydome.fragment.NewsFragment
import com.wangbin.mydome.fragment.TestFragment
import com.wangbin.mydome.helper.BottomNavigationViewHelper

import java.util.ArrayList


/**
 * 主界面
 */
class MainActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null
    private var nav_view: NavigationView? = null
    private var drawer_layout: DrawerLayout? = null
    private var rec_search: RecyclerView? = null
    private var et_search: EditText? = null
    private var img_search: ImageView? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var searchAdapter: SearchAdapter? = null
    private var viewPager: ViewPager? = null
    private var menuItem: MenuItem? = null
    private var num = 1
    private val datas = ArrayList<SearchBean>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        menuItem = item
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager!!.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                viewPager!!.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewPager!!.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_person -> {
                viewPager!!.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initLinster()
    }

    private fun initView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView!!)
        viewPager = findViewById(R.id.vp)
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (menuItem != null) {
                    menuItem!!.isChecked = false
                } else {
                    bottomNavigationView!!.menu.getItem(0).isChecked = false
                }
                menuItem = bottomNavigationView!!.menu.getItem(position)
                menuItem!!.isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = viewPagerAdapter
        val list = ArrayList<Fragment>()
        list.add(NewsFragment.newInstance("首页"))
        list.add(NewsFragment.newInstance("公告"))
        list.add(TestFragment.newInstance("趣闻"))
        list.add(TestFragment.newInstance("个人"))
        viewPagerAdapter!!.setList(list)

        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        val headerView = nav_view!!.getHeaderView(0)
        rec_search = headerView.findViewById(R.id.rec_search)
        et_search = headerView.findViewById(R.id.et_search)
        img_search = headerView.findViewById(R.id.img_search)

        val layoutManager = LinearLayoutManager(this@MainActivity)
        rec_search!!.layoutManager = layoutManager
        searchAdapter = SearchAdapter(this@MainActivity, datas)
        rec_search!!.adapter = searchAdapter
    }

    private fun initLinster() {
        img_search!!.setOnClickListener {
            datas.clear()
            for (i in 0..4) {
                val searchBean = SearchBean()
                searchBean.name = "测试数据$num"
                searchBean.isFlag = false
                datas.add(searchBean)
                num++
            }
            searchAdapter!!.notifyDataSetChanged()
        }

        searchAdapter!!.setOnItemClickLitener { view, position ->
            for (searchBean in datas) {
                searchBean.isFlag = false
            }
            datas[position].isFlag = true
            searchAdapter!!.notifyDataSetChanged()
        }

    }

}
