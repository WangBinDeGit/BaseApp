package com.wangbin.mydome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wangbin.mydome.R
import com.wangbin.mydome.adapter.BaseRecyclerAdapter
import com.wangbin.mydome.adapter.TestAdapter1
import com.wangbin.mydome.base.BaseActivity
import com.wangbin.mydome.base.BaseViewHolder
import com.wangbin.mydome.tools.GlideImageLoader
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.headtoolbar.*
import java.util.*

class TestActivity : BaseActivity() {
    private var mData: List<String>? = null

    override fun intiLayout(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        tv_head_title.text = "测试热修复"
        GlideImageLoader.loadCircleImage(this,"http://wx1.sinaimg.cn/large/d2e27164gy1fbto0raczsj21hc0xyx12.jpg",img_circle)
        GlideImageLoader.loadRoundCornerImage(this,"http://wx1.sinaimg.cn/large/d2e27164gy1fbto0raczsj21hc0xyx12.jpg",img_rount,20)
        mData = Arrays.asList("12312", "1231", "9237bb9443", "MyApp")
//        val adapter = TestAdapter(this, mData!!)
        val adapter = TestAdapter1(this, mData!!)
        adapter.setOnItemClickListener(object : BaseRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(holder: BaseViewHolder, v: View, position: Int) {
//                holder.setText(R.id.item_search, "被点击了吖")
                startActivity(MainActivity::class.java)
            }
        })
        rcy_test.layoutManager = LinearLayoutManager(this)
        rcy_test.adapter = adapter
    }

    override fun setListener() {
    }

    override fun widgetClick(view: View) {
    }

}
