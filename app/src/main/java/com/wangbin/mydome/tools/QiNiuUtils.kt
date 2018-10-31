package com.wangbin.mydome.tools

import android.os.Handler
import android.os.Message
import android.util.Log
import com.wangbin.mydome.Constant.Companion.constant
import com.wangbin.mydome.MyApplication
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UpProgressHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import com.qiniu.android.utils.UrlSafeBase64
import com.wangbin.mydome.MyApplication.Companion.instance
import com.wangbin.mydome.bean.UpdatePictureBean
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * 七牛上传工具类
 *
 * @version V1.0
 * @Title: ${FILE_NAME}
 * @Package com.mykotlin.Tools
 * @Description: ${todo}
 */
object QiNiuUtils {
    //七牛后台的key
    private val AccessKey = "68WVZh7y55bBAAHAvN0GVi5P2nd5KUuiVLBou3q1"
    //七牛后台的secret
    private val SecretKey = "Bl54uytxBQq_aEX_DlennwzxDGVACjebjQxTO7K2"

    private val MAC_NAME = "HmacSHA1"
    private val ENCODING = "UTF-8"
    private var picName = ""

    fun uploadPic(path: String, position: String, type: String, headpic: String, handler: Handler?): String {
        val keys = getPicName(type)
        val bucketName = "meijia-image"
        try {
            // 1:第一种方式 构造上传策略
            val _json = JSONObject()
            val _dataline = System.currentTimeMillis() / 1000 + 3600
            _json.put("deadline", _dataline)// 有效时间为一个小时
            _json.put("scope", bucketName)
            val _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().toByteArray())
            val _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey)
            val _encodedSign = UrlSafeBase64.encodeToString(_sign)
            val _uploadToken = (AccessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy)
            //此处为模拟的本地相册的一个图片
            val uploadManager = UploadManager()
            //以下api可以修改为图片地址 或者file  或者byte[]
            uploadManager.put(path, keys, _uploadToken,
                    object : UpCompletionHandler {
                        override fun complete(key: String?, info: ResponseInfo?,
                                              response: JSONObject?) {
                            if (info!!.isOK) {
                                picName = key!!
                                val addFileBean = UpdatePictureBean()
                                addFileBean.oldname = position
                                addFileBean.path = path
                                addFileBean.name = picName
                                Log.e("mylog", picName + "......")
                                val msg = Message()
                                msg.obj = addFileBean
                                if (headpic == "headpic") {
                                    msg.what = constant.TWO
                                } else if (headpic == "refuse") {
                                    msg.what = constant.THREE
                                } else if (headpic == "finish") {
                                    msg.what = constant.FOUR
                                } else if (headpic == "idcard_z") {
                                    msg.what = constant.FIVE
                                } else if (headpic == "idcard_f") {
                                    msg.what = constant.SIX
                                }
                                handler!!.sendMessage(msg)
                            } else {
                                ToastUtils.showShortToast(instance.applicationContext, "上传失败")
                            }
                        }
                    }, UploadOptions(null, null, false,
                    UpProgressHandler { key, percent ->
                        Log.i("qiniu", "$key: $percent")
                    }, null))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return picName
    }

    /**
     * 这个签名方法找了半天 一个个对出来的、、、、程序猿辛苦啊、、、 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun HmacSHA1Encrypt(encryptText: String, encryptKey: String): ByteArray {
        val data = encryptKey.toByteArray(charset(ENCODING))
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        val secretKey = SecretKeySpec(data, MAC_NAME)
        // 生成一个指定 Mac 算法 的 Mac 对象
        val mac = Mac.getInstance(MAC_NAME)
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey)
        val text = encryptText.toByteArray(charset(ENCODING))
        // 完成 Mac 操作
        return mac.doFinal(text)
    }


    private fun getPicName(type: String): String {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"
        val sb = StringBuffer("")
        sb.append(getdata())
        for (i in 0..7) {
            sb.append(chars[(Math.random() * 61).toInt()])
        }
        sb.append("." + type)
        return sb.toString()
    }

    fun getdata(): String {
        val now = Date()
        val dateFormat = SimpleDateFormat("yyyyMMddhhmmss")
        return dateFormat.format(now)
    }

}