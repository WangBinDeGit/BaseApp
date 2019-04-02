package com.wangbin.mydome.tools

/**
 * @ClassName PhoneNumUtils
 * @Description 验证手机格式
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
class PhoneNumUtils {

    companion object {
        fun isMobile(number: String): Boolean {
            /**
            移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
            联通：130、131、132、152、155、156、185、186
            电信：133、153、180、189、（1349卫通）
            总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
            */
            val num = "[1][3456789]\\d{9}"//"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            return if (number.isEmpty()) {
                false
            } else {
                //matches():字符串是否在给定的正则表达式匹配
                number.matches(num.toRegex())
            }
        }
    }

}
