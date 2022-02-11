package com.mind.data.di.interceptor

import com.example.projectdemo.config.MsgEvent.TOKEN_OUT
import com.example.projectdemo.data.model.bean.Res
import com.example.projectdemo.data.model.bean.ResCode
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body != null) {
            val body = response.body?.string()
            val model = Gson().fromJson(body, Res::class.java)
            if (model.code == 99999) {
                LiveEventBus.get(TOKEN_OUT).post(model.msg)
            }
            val responseBody = body?.toResponseBody()
            response.newBuilder().body(responseBody).build()
        } else {
            response
        }

    }
}