package com.example.projectdemo.network.interceptor


import com.example.projectdemo.App
import com.example.projectdemo.util.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(var day:Int=7):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request=chain.request()
        if (!NetworkUtil.isNetworkAvailable(App.mApplication))
        {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        val response=chain.proceed(request)
        if (!NetworkUtil.isNetworkAvailable(App.mApplication))
        {
            val maxAge=60*60
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control","public,max-age=$maxAge")
                .build()
        }else{
            val maxStale=60*60*24*day
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control","public,only-if-cached,max-stale=$maxStale")
                .build()
        }
        return response
    }
}