package com.example.projectdemo.network

import com.example.projectdemo.data.model.bean.*
import retrofit2.http.*

interface ApiService2 {
    companion object{
        const val SERVER_URL="https://wanandroid.com/"
    }



    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id")id:Int):ApiResponse<Any>


    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id")id:Int):ApiResponse<Any>


    @GET("article/list/{page}/json")
    suspend fun getAritrilList(@Path("page") pageNo: Int): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>

    @GET("article/top/json")
    suspend fun getTopAritrilList():ApiResponse<ArrayList<AriticleResponse>>

    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<ArrayList<BannerResponse>>


    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username")username:String,
        @Field("password")pwd:String,
    ):ApiResponse<UserInfo>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun register(
        @Field("username")username:String,
        @Field("password")pwd:String,
        @Field("repassword")rpwd:String
    ):ApiResponse<Any>
}