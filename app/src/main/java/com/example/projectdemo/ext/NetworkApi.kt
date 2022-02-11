package com.example.projectdemo.ext


import com.example.projectdemo.App
import com.example.projectdemo.network.ApiService2
import com.example.projectdemo.network.BaseNetworkApi
import com.example.projectdemo.network.interceptor.CacheInterceptor
import com.example.projectdemo.network.interceptor.MyHeadInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val apiService: ApiService2 by lazy (mode=LazyThreadSafetyMode.SYNCHRONIZED) {
           NetworkApi.INSTANCE.getApi(ApiService2::class.java,ApiService2.SERVER_URL)
}
class NetworkApi: BaseNetworkApi()
{
    companion object{
        val INSTANCE:NetworkApi by lazy ( mode=LazyThreadSafetyMode.SYNCHRONIZED ){
            NetworkApi()
        }
    }
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
                  builder.apply {
                      cache(Cache(File(App.mApplication.cacheDir,"cxk_cache"),10*1024*1024))

                      cookieJar(cookieJar)

                      addInterceptor(MyHeadInterceptor())

                      addInterceptor(CacheInterceptor())

                    //  addInterceptor(LogInterceptor())

                      connectTimeout(10, TimeUnit.SECONDS)

                      readTimeout(5,TimeUnit.SECONDS)

                      writeTimeout(5,TimeUnit.SECONDS)

                  }
        return builder
    }

    override fun setRetorfitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
                      return builder.apply {
                          addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                      }
    }


    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.mApplication))
    }

}