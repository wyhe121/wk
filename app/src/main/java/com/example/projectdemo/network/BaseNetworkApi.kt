package com.example.projectdemo.network

import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit

abstract class BaseNetworkApi {

    fun <T> getApi(serviceClass:Class<T>,baseUrl:String):T
    {
        val retorfitBuilder= Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okhttpClient)
        return setRetorfitBuilder(retorfitBuilder).build().create(serviceClass)

    }
    private val okhttpClient:OkHttpClient
        get() {
            var builder= RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
            builder=setHttpClientBuilder(builder)
            return builder.build()
        }

    abstract fun setHttpClientBuilder(builder:OkHttpClient.Builder):OkHttpClient.Builder
    abstract fun setRetorfitBuilder(builder: Retrofit.Builder):Retrofit.Builder

}