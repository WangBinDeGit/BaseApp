package com.wangbin.mydome.net

import okhttp3.FormBody
import okhttp3.RequestBody

/**
 * 添加請求參數方法
 */
class RequestParam {
    private object Holder {
        val INSTANCE = RequestParam()
    }

    companion object {
        val REQUEST: RequestParam by lazy { Holder.INSTANCE }
    }

    var token = "OYh5XgZW9q3qalGROmyWfTmnBXhN06mx"
    var timestamp = "1515739293936"
    var sign = "16e33357c2937c96f8d5c5fd42dd0e1c"
    val APPID = "wx02dc122167633c95"


    fun getNoRequestBody(): RequestBody {
        val body: RequestBody = FormBody.Builder()
                .add("access-token", token)
                .add("timestamp", timestamp)
                .add("sign", sign)
                .build()
        return body
    }

    fun getSearchAreaRequestBody(): RequestBody {
        val body: RequestBody = FormBody.Builder()
                .add("Token", "meijia20180318")
                .build()
        return body
    }

    fun getRequestBody(page: Int): RequestBody {
        val body: RequestBody = FormBody.Builder()
                .add("access-token", token)
                .add("timestamp", timestamp)
                .add("sign", sign)
                .add("page", page.toString())
                .build()
        return body
//        val mbody = MultipartBody.Builder().setType(MultipartBody.FORM)
//        mbody.addFormDataPart("access-token", token)
//        mbody.addFormDataPart("timestamp", timestamp)
//        mbody.addFormDataPart("sign", sign)
//        mbody.addFormDataPart("page", page.toString())
//        val body: RequestBody = mbody.build()
//        return body
    }

//    fun getRequestBodyPic(photoName: ArrayList<UpdatePictureBean>, datas: List<ContactsListBean>): RequestBody {
//        val sb = StringBuffer()
//        for (i in photoName.indices) {
//            sb.append(photoName[i].name + "-")
//            sb.append((FileSizeUtil.getFileOrFilesSize(photoName[i].path, FileSizeUtil.SIZETYPE_KB) as Int).toString())
//            if (i != photoName.size - 1) {
//                sb.append(",")
//            }
//        }
//        val mbody = MultipartBody.Builder().setType(MultipartBody.FORM)
//        mbody.addFormDataPart("access-token", token)
//        mbody.addFormDataPart("timestamp", timestamp)
//        mbody.addFormDataPart("sign", sign)
//        mbody.addFormDataPart("pic", sb.toString())
//        for (i in datas.indices) {
//            mbody.addFormDataPart("title", datas[i].title)
//            mbody.addFormDataPart("address", datas[i].address)
//            mbody.addFormDataPart("content", datas[i].content)
//        }
//        val body: RequestBody = mbody.build()
//        return body
//    }
}