package com.wangbin.mydome.net

import com.wangbin.mydome.bean.BaseEntity
import com.wangbin.mydome.bean.UserBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 接口传值Service
 */
interface ApiService {

    companion object {
        private const val LOGINURL = "login/loginByName"
        private const val INDEXURL = "v1/index"
        private const val NOTICEURL = "v1/index/notice"
        private const val HOUSEURL = "v1/house"
    }

    //登录
    @POST(LOGINURL)
    @FormUrlEncoded
    fun login(@Field("userName") userName: String,
              @Field("passWord") passWord: String): Observable<BaseEntity<UserBean>>

    //首页
    @POST(INDEXURL)
    fun index(): Observable<BaseEntity<UserBean>>

    //公告
    @POST(NOTICEURL)
    fun notice(@Query("page") page: Int): Observable<BaseEntity<UserBean>>

    /***房源搜索
     * 小区名字	community_name
     * 房源编号	house_no
     * 状态	status
     * 开始时间	start_date
     * 结束时间	end_date
     */
    @POST(HOUSEURL)
    @FormUrlEncoded
    fun housingResourceSearch(@Field("community_name") community_name: String,
                              @Field("house_no") house_no: String,
                              @Field("status") status: String,
                              @Field("start_date") start_date: String,
                              @Field("end_date") end_date: String,
                              @Query("page") page: Int): Observable<BaseEntity<UserBean>>

}