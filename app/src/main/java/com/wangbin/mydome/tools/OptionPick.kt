package com.wangbin.mydome.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.wangbin.mydome.R


/**
 * 三列条件选择器
 */
class OptionPick {
    private object Holder {
        val INSTANCE = OptionPick()
    }

    companion object {
        val optionPick: OptionPick by lazy { Holder.INSTANCE }
    }


    /***
     * 三列条件选择器-不联动
     * options1Items 第一列的数据
     * item1Unit  第一列的单位名称
     */
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    fun setOptionPick(context: Activity, tv: TextView, title: String, options1Items: ArrayList<String>,
                      item1Unit: String, options2Items: ArrayList<String>, item2Unit: String,
                      options3Items: ArrayList<String>, item3Unit: String): ArrayList<Int> {
        if (context.currentFocus != null) {
            val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
        val positions = ArrayList<Int>();
        //条件选择器
        val pvOptions: OptionsPickerView<String>? = OptionsPickerBuilder(context, OnOptionsSelectListener { option1, option2, option3, _ ->
            //返回的分别是三个级别的选中位置 ;位置从0开始
            tv.text = options1Items[option1] + item1Unit + options2Items[option2] + item2Unit + options3Items[option3] + item3Unit
            positions.add(option1 + 1)
            positions.add(option2 + 1)
            positions.add(option3 + 1)


        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(14)//确定和取消文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(context.resources.getColor(R.color.app_color))//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(16)//滚轮文字大小
                .setLabels(item1Unit, item2Unit, item3Unit)//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(2, 0, 0)  //设置默认选中项
                .build();

        //不联动
        pvOptions!!.setNPicker(options1Items, options2Items, options3Items)
        pvOptions.show()

        return positions
    }


}