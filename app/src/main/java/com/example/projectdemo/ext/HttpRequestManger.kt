package com.example.projectdemo.ext


import com.example.projectdemo.data.model.bean.ApiPagerResponse
import com.example.projectdemo.data.model.bean.ApiResponse
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.data.model.bean.UserInfo
import com.example.projectdemo.network.AppException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

val HttpRequestCoroutine:HttpRequestManager by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED)
{
    HttpRequestManager()
}
class HttpRequestManager
{
    suspend fun getHomeData(pageNo:Int): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>> {
        return withContext(Dispatchers.IO)
        {
            val listData=async {apiService.getAritrilList(pageNo) }
            if (pageNo==0)
            {
                val topData=async { apiService.getTopAritrilList() }
               listData.await().data.datas.addAll(0,topData.await().data)
               listData.await()
            }else{
                 listData.await()
            }
        }
    }
    suspend fun register(username:String,password:String):ApiResponse<UserInfo>
    {
        val registerData= apiService.register(username,password,password)
        if (registerData.isSuccess())
        {
            return apiService.login(username,password)
        }else{
            throw AppException(registerData.errorCode,registerData.errorMsg)
        }
    }
}
