package com.wangbin.mydome.net

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.wangbin.mydome.tools.HeaderUtils
import com.wangbin.mydome.Constant
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by WangBin
 * on 2018/8/8.
 */
class OkHttpUtils {

    private object Holder {
        val INSTANCE = OkHttpUtils()
    }

    companion object {
        val instance: OkHttpUtils by lazy { Holder.INSTANCE }
    }


    /**
     * get请求方式
     */
    fun getAsynHttp(url: String, handler: Handler, index: Int) {
        try {
            //创建网络处理的对象
            val client = OkHttpClient.Builder()
                    //设置读取数据的时间
                    .readTimeout(5, TimeUnit.SECONDS)
                    //对象的创建
                    .build()
            //创建一个网络请求的对象，如果没有写请求方式，默认的是get
            //在请求对象里面传入链接的URL地址
            val request = Request.Builder()
                    .url(url).build()

            //call就是我们可以执行的请求类
            val call = client.newCall(request)
            //异步方法，来执行任务的处理，一般都是使用异步方法执行的
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //失败
                    Log.e("mylog", Thread.currentThread().name + "结果  " + e.toString())
                    val message = Message()
                    message.obj = e.toString()
                    message.what = index
                    handler.sendMessage(message)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    //成功
                    //子线程
                    //main thread1
                    Log.e("mylog", Thread.currentThread().name + "结果  " + response.body()!!.string())
                    val message = Message()
                    message.obj = response.body()!!.string()
                    message.what = index
                    handler.sendMessage(message)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //  call.cancel();取消任务

        //同步方法,一般不用
        /* try {
            Response execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * post请求方式，请求网络数据
     */
    fun postAsynHttp(context: Context, url: String, body: RequestBody, handler: Handler, index: Int) {

        //创建网络处理的对象
        val client = OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build()

        //post请求来获得数据
        //创建一个RequestBody，存放重要数据的键值对
//        var body2: RequestBody = FormBody.Builder()
//                .add("access-token", "OYh5XgZW9q3qalGROmyWfTmnBXhN06mx")
//                .add("timestamp", "1515739293936")
//                .add("sign", "16e33357c2937c96f8d5c5fd42dd0e1c")
//                .build();
        //创建一个请求对象，传入URL地址和相关数据的键值对的对象
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        //创建一个能处理请求数据的操作类
        val call = client.newCall(request)
        //使用异步任务的模式请求数据
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val result = e.toString()
                Log.e(Constant.constant.TAG, "错误信息：$result")
                val message = Message()
                message.obj = result
                message.what = Constant().FAILPATH
                handler.sendMessage(message)
                Thread(Runnable {
                    Looper.prepare()
                    toastError(context)
                    Looper.loop()
                }).start();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val result = response.body()!!.string()
                Log.e(Constant.constant.TAG, result + response.code())
                try {
                    if (!TextUtils.isEmpty(response.header(HeaderUtils.MAXPAGE)) && !TextUtils.isEmpty(response.header(HeaderUtils.MAXCOUNT))) {
                        val maxPage = Integer.parseInt(response.header(HeaderUtils.MAXPAGE))
                        val maxCount = Integer.parseInt(response.header(HeaderUtils.MAXCOUNT))
                        HeaderUtils.maxpage = maxPage
                        HeaderUtils.maxCount = maxCount
                    }
                    val message = Message()
                    message.obj = result
                    message.what = index
                    handler.sendMessage(message)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    /**
     * 设置请求头
     * @param headersParams
     * @return
     */
    private fun SetHeaders(headersParams: Map<String, String>?): Headers {
        val headers: Headers
        val headersbuilder = okhttp3.Headers.Builder()

        if (headersParams != null) {
            val iterator = headersParams.keys.iterator()
            var key: String
            while (iterator.hasNext()) {
                key = iterator.next()
                headersbuilder.add(key, headersParams[key]!!)
                Log.d(Constant.constant.TAG, "get_headers===" + key + "====" + headersParams[key])
            }
        }
        headers = headersbuilder.build()

        return headers
    }

    fun toastError(context: Context) {
        Toast.makeText(context, "请求失败，请检查网络配置!", Toast.LENGTH_SHORT).show()
    }
}



