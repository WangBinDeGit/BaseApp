package com.wangbin.mydome.net

import android.util.Log
import com.wangbin.mydome.Constant
import okhttp3.*
import okio.Buffer
import java.io.IOException

class LogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logRequest(request)
        val response = chain.proceed(request)
        return logResponse(response)
    }


    private fun logRequest(request: Request) {
        try {
            val url = request.url().toString()
            val headers = request.headers()

            Log.d(Constant.constant.TAG, "url : $url")
            Log.d(Constant.constant.TAG, "method : " + request.method())
            if (headers != null && headers.size() > 0) {
                Log.e(Constant.constant.TAG, "headers : " + headers.toString())
            }
            val requestBody = request.body()
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        Log.d(Constant.constant.TAG, "params : " + bodyToString(request))
                    } else {
                        Log.d(Constant.constant.TAG, "params : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun logResponse(response: Response): Response {
        try {
            val builder = response.newBuilder()
            val clone = builder.build()
            var body = clone.body()
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        val resp = body.string()
                        Log.d(Constant.constant.TAG, resp)

                        body = ResponseBody.create(mediaType, resp)
                        return response.newBuilder().body(body).build()
                    } else {
                        Log.d(Constant.constant.TAG, "data : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }

        } catch (e: Exception) {
        }

        return response
    }


    private fun isText(mediaType: MediaType?): Boolean {
        return if (mediaType == null) false else "text" == mediaType.subtype()
                || "plain" == mediaType.subtype()
                || "json" == mediaType.subtype()
                || "xml" == mediaType.subtype()
                || "html" == mediaType.subtype()
                || "webviewhtml" == mediaType.subtype()
                || "x-www-form-urlencoded" == mediaType.subtype()
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }
}