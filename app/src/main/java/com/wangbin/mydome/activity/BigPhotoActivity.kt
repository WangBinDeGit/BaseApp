package com.wangbin.mydome.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.wangbin.mydome.R
import com.wangbin.mydome.base.BaseActivity
import com.wangbin.mydome.tools.Logger
import kotlinx.android.synthetic.main.activity_big_photo.*
import kotlinx.android.synthetic.main.headtoolbar.*
import java.util.*

/**
 * Created by WangBin on 2018/4/17.
 * 看大图
 */

class BigPhotoActivity : BaseActivity() {


    private var count: Int = 0
    private var datas: ArrayList<String>? = null
    private var datastype: ArrayList<String>? = null
    private var type = ""
    private var position = ""

    override fun initParams(arguments: Bundle?) {
        datas = this.intent.getStringArrayListExtra("datas")
        datastype = this.intent.getStringArrayListExtra("datastype")
        type = this.intent.getStringExtra("type")
        position = this.intent.getStringExtra("position")
    }

    override fun intiLayout(): Int {
        return R.layout.activity_big_photo
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        hvp_bigphoto.addOnPageChangeListener(pageChangeListener)
        hvp_bigphoto.adapter = SamplePagerAdapter(this@BigPhotoActivity, datas!!)
        hvp_bigphoto.currentItem = Integer.valueOf(position)!!
        if (datastype != null && datastype!!.size > 0) {
            for (i in datastype!!.indices) {
                Logger.d(TAG, datastype!![i])
            }
            tv_head_title.text = datastype!!.get(index = Integer.valueOf(position)!!)
        } else {
            tv_head_title.text = resources.getText(R.string.photo_title)
        }
        tv_bigphoto_num.text = (Integer.valueOf(position) + 1).toString() + "/" + datas!!.size
    }

    override fun setListener() {
        finishthis(this, img_head_left)
    }

    override fun widgetClick(view: View) {
    }

    private val pageChangeListener = object : ViewPager.OnPageChangeListener {
        @SuppressLint("SetTextI18n")
        override fun onPageSelected(position: Int) {
            count = position
            //			int newPosition = position % application.phototype.size();
            //设置文本
            tv_bigphoto_num.text = (Integer.valueOf(position) + 1).toString() + "/" + datas!!.size
            if (datastype != null && datastype!!.size > 0) {
                tv_head_title.text = datastype!![position]
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

        override fun onPageScrollStateChanged(arg0: Int) {}
    }


    internal inner class SamplePagerAdapter(private val context: Context, private val listViews: ArrayList<String>) : PagerAdapter() {
        override fun getCount(): Int {
            return listViews.size
        }


        override fun instantiateItem(container: ViewGroup, position: Int): View {
            val photoView = PhotoView(container.context)
            val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
            photoView.layoutParams = lp
            Glide.with(context)
                    .load(listViews[position]) //图片地址
                    .asBitmap()
                    .into(photoView)

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

    }


}