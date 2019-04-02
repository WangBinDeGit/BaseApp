package com.wangbin.mydome.tools.encryption

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @ClassName CreateMD5
 * @Description 进行MD%加密
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
class CreateMD5 {
    //静态方法，便于作为工具类
    companion object {

        fun getMd5(plainText: String): String? {
            try {
                val md = MessageDigest.getInstance("MD5")
                md.update(plainText.toByteArray())
                val b = md.digest()

                var i: Int

                val buf = StringBuffer("")
                for (offset in b.indices) {
                    i = b[offset].toInt()
                    if (i < 0)
                        i += 256
                    if (i < 16)
                        buf.append("0")
                    buf.append(Integer.toHexString(i))
                }
                //32位加密
                return buf.toString()
                // 16位的加密
                //return buf.toString().substring(8, 24);
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                return null
            }
        }
    }
}