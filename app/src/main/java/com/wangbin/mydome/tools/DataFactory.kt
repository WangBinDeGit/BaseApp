package com.wangbin.mydome.tools

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by WangBin on 2018/1/12.
 *
 * Json解析类
 */

object DataFactory {

    /**
     * @author WangBin
     * @param json
     * @param clazz
     * @return
     */
    fun getInstanceByJson(clazz: Class<*>, json: String): Any? {
        val obj: Any?
        val gson = Gson()
        obj = gson.fromJson(json, clazz)
        return obj
    }

    /**
     * @author WangBin
     * @param json
     * @param clazz
     * @return
     */
    fun <T> jsonToList(json: String, clazz: Class<Array<T>>): List<T> {
        val gson = Gson()
        val array = gson.fromJson(json, clazz)
        return Arrays.asList(*array)
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    fun <T> jsonToArrayList(json: String, clazz: Class<T>): ArrayList<T> {
        val type = object : TypeToken<ArrayList<JsonObject>>() {

        }.type
        val jsonObjects = Gson().fromJson<ArrayList<JsonObject>>(json, type)

        val arrayList = ArrayList<T>()
        for (jsonObject in jsonObjects) {
            arrayList.add(Gson().fromJson(jsonObject, clazz))
        }
        return arrayList
    }
}