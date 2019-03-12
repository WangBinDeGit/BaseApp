package com.wangbin.mydome.activity

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.vector.update_app.UpdateAppManager
import com.wangbin.mydome.adapter.SearchAdapter
import com.wangbin.mydome.adapter.ViewPagerAdapter
import com.wangbin.mydome.base.BaseActivity
import com.wangbin.mydome.bean.SearchBean
import com.wangbin.mydome.fragment.IndexFragment
import com.wangbin.mydome.fragment.TestFragment
import com.wangbin.mydome.helper.BottomNavigationViewHelper
import com.wangbin.mydome.net.UpdateAppHttpUtil
import com.wangbin.mydome.net.Url.Companion.url
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


/**
 * 主界面
 */
class MainActivity : BaseActivity() {

    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var searchAdapter: SearchAdapter? = null
    private var menuItem: MenuItem? = null
    private var num = 1
    private val mDatas = ArrayList<SearchBean>()
    private var titles: Array<String>? = null
    private var imgSearch: ImageView? = null

    override fun intiLayout(): Int {
        return com.wangbin.mydome.R.layout.activity_main
    }

    override fun widgetClick(view: View) {
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        menuItem = item
        when (item.itemId) {
            com.wangbin.mydome.R.id.navigation_home -> {
                viewPager!!.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            com.wangbin.mydome.R.id.navigation_dashboard -> {
                viewPager!!.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            com.wangbin.mydome.R.id.navigation_notifications -> {
                viewPager!!.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            com.wangbin.mydome.R.id.navigation_person -> {
                viewPager!!.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initView() {
        titles = arrayOf(resources.getString(com.wangbin.mydome.R.string.title1), resources.getString(com.wangbin.mydome.R.string.title2),
                resources.getString(com.wangbin.mydome.R.string.title3), resources.getString(com.wangbin.mydome.R.string.title3))
        bottomNavigationView!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView!!)
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
        list.add(IndexFragment.newInstance(titles!![0]))
        list.add(TestFragment.newInstance(titles!![1]))
        list.add(TestFragment.newInstance(titles!![2]))
        list.add(TestFragment.newInstance(titles!![3]))
        viewPagerAdapter!!.setList(list)

        val headerView = nav_view!!.getHeaderView(0)
        val recSearch = headerView.findViewById<RecyclerView>(com.wangbin.mydome.R.id.rec_search)
        imgSearch = headerView.findViewById(com.wangbin.mydome.R.id.img_search)

        recSearch!!.layoutManager = LinearLayoutManager(this@MainActivity)
        searchAdapter = SearchAdapter(this@MainActivity, mDatas)
        recSearch.adapter = searchAdapter
        UpdateAppManager.Builder()
                //当前 Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl(url.BASE_URL + url.UPDATE)
                //实现 httpManager 接口的对象
                .setHttpManager(UpdateAppHttpUtil())
                .build()
                .update()
    }

    override fun setListener() {
        imgSearch!!.setOnClickListener {
            mDatas.clear()
            for (i in 0..4) {
                val searchBean = SearchBean()
                searchBean.name = "测试数据$num"
                searchBean.isFlag = false
                mDatas.add(searchBean)
                num++
            }
            searchAdapter!!.notifyDataSetChanged()
        }

        searchAdapter!!.setOnItemClickLitener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                for (searchBean in mDatas) {
                    searchBean.isFlag = false
                }
                mDatas[position].isFlag = true
                searchAdapter!!.notifyDataSetChanged()
            }
        })
    }

}
