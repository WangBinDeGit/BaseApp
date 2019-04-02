package com.wangbin.mydome.tools

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @ClassName DataFactory
 * @Description json的解析
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
object DataFactory {

    /**
     * @param json      传入字符串
     * @param clazz     传入转换类
     * @return 返回对象
     */
    fun getInstanceByJson(clazz: Class<*>, json: String): Any? {
        val obj: Any?
        val gson = Gson()
        obj = gson.fromJson(json, clazz)
        return obj
    }

    /**
     * @param json      传入字符串
     * @param clazz     传入转换类
     * @return 返回对象列表
     */
    fun <T> jsonToList(json: String, clazz: Class<Array<T>>): List<T> {
        val gson = Gson()
        val array = gson.fromJson(json, clazz)
        return Arrays.asList(*array)
    }

    /**
     * @param json      传入字符串
     * @param clazz     传入转换类
     * @return 返回对象列表
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