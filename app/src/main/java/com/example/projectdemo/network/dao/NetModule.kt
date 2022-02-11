package com.example.projectdemo.network.dao

import com.example.projectdemo.config.AppConfig
import com.example.projectdemo.network.ServiceCreator
import com.mind.data.di.interceptor.RequestInterceptor
import com.mind.data.di.interceptor.ResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        // --------https校验  支持单项校验和双向校验--------------------
//        val sslParams = HttpsUtils.getSslSocketFactory(
//            // assets 目录下的.cer 文件 后台或运维给的文件
//            BaseApp.getApp().assets.open("xxx.cer")
//        )
//        if (sslParams.sSLSocketFactory != null &&
//            sslParams.trustManager != null
//        ) {
//            builder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
//            builder.hostnameVerifier(SafeHostnameVerifier())
//        }
        return builder
//            .addInterceptor(RequestInterceptor())
//            .addInterceptor(ResponseInterceptor())
//            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConfig.BASE_URL)
            .client(okHttpClient)
            .build()
}