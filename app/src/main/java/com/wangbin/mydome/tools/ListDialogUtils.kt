package com.wangbin.mydome.tools

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.wangbin.mydome.adapter.CommunityAdapter
import com.wangbin.mydome.R
import com.wangbin.mydome.bean.CommunityModel
import java.util.*

/**
 * Created by wangbin
 * on 2017/8/8.
 * 列表式弹框
 */
class ListDialogUtils {

    fun showAddHouseListDialog(context: Context, view: View, dataList: ArrayList<CommunityModel>, click: OnItemClick) {

        val popview = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)
        val listPop = PopupWindow(popview)
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
//        listPop!!.animationStyle = R.style.mypopwindow_anim_style
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
//        listPop!!.isFocusable = true
        listPop.setBackgroundDrawable(BitmapDrawable())
        listPop.isOutsideTouchable = true
        listPop.width = view.width
        listPop.height = LinearLayout.LayoutParams.WRAP_CONTENT
        if (dataList.size > 0)
            listPop.showAsDropDown(view, -25, 0)

        val rcvhouse = popview!!.findViewById<RecyclerView>(R.id.rcv_dialog_house) as RecyclerView
        val layoutmanager = LinearLayoutManager(context)
        rcvhouse.layoutManager = layoutmanager
        val houseadapter = CommunityAdapter(context, dataList)
        rcvhouse.adapter = houseadapter
        houseadapter.setItemClickListener(object : CommunityAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                click.onJump(dataList[position])
                listPop.dismiss()
            }
        })
    }


    private object Holder {
        val INSTANCE = ListDialogUtils()
    }

    companion object {
        val listDialog: ListDialogUtils by lazy { Holder.INSTANCE }
        var pw: PopupWindow? = null
    }

    interface OnItemClick {
        fun onJump(item: CommunityModel)
    }
}