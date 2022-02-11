package com.example.projectdemo.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object NetWork {
    private val ariticleResponse = ServiceCreator.create(ApiService::class.java)

  //  suspend fun getAritrilList(pageNo:Int) = ariticleResponse.getAritrilList(pageNo).await()

    suspend fun getProjecNewData(pageNo:Int) = ariticleResponse.getProjecNewData(pageNo).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null)
                        it.resume(body)
                    else it.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                 it.resumeWithException(RuntimeException(t))
                }

            })
        }

    }
}