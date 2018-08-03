package com.wangbin.mydome.net

import com.wangbin.mydome.bean.LoginModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 接口传值Service
 */
interface ApiService {

    //登录
    @POST("v1/login")
    fun login(): Observable<LoginModel>

    //首页
    @POST("v1/index")
    fun index(): Observable<LoginModel>

    //公告
    @POST("v1/index/notice")
    fun notice(@Query("page") page: Int): Observable<LoginModel>

    /***房源搜索
     * 小区名字	community_name
     * 房源编号	house_no
     * 状态	status
     * 开始时间	start_date
     * 结束时间	end_date
     */
    @POST("v1/house")
    @FormUrlEncoded
    fun housingResourceSearch(@Field("community_name") community_name: String,
                              @Field("house_no") house_no: String,
                              @Field("status") status: String,
                              @Field("start_date") start_date: String,
                              @Field("end_date") end_date: String,
                              @Query("page") page: Int): Observable<LoginModel>

}