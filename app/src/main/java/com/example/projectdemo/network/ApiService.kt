package com.example.projectdemo.network

import com.example.projectdemo.data.model.bean.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("article/list/{page}/json")
    fun getAritrilList(@Path("page") pageNo:Int): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>

    @GET("article/listproject/{page}/json")
    fun getProjecNewData(@Path("page") pageNo:Int): Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>

//    @POST("article/list")
//    suspend fun getArticle(@Body map:HashMap<String,String>): Res<PageList<ArticleModel>>
     @GET("article/list/{page}/json")
    suspend fun getArticle(@Path("page") pageNo:Int):ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>
}