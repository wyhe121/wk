package com.example.projectdemo.network

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.liveData
import com.bumptech.glide.Glide.init
import com.example.projectdemo.data.model.bean.ApiPagerResponse
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.data.model.bean.TagsResponse
import com.example.projectdemo.network.dao.AriteilDao
import com.example.projectdemo.network.dao.DataStoreBase
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONObject
import java.lang.RuntimeException
import java.util.concurrent.Flow
import kotlin.coroutines.CoroutineContext

object Respository {

    suspend fun saveAritrilViewModel(AriticleResponse: ArrayList<AriticleResponse>) = AriteilDao.savePlace(AriticleResponse);

    suspend fun isSave()=AriteilDao.isSavePlace()

    suspend fun getAritrilViewModel():ArrayList<AriticleResponse>{

        var data= AriteilDao.getPlace()
         Log.d("a3","data="+data.toString())
        val api :ArrayList<AriticleResponse> = arrayListOf()

        val jsonArray=JSONArray(data)

//        for ( i in 0..jsonArray.length()-1){
//            val ariticleResponse=AriticleResponse()
//            val jsonObject=jsonArray.getJSONObject(i)
//            ariticleResponse.apkLink=jsonObject.getString("apkLink")
//            ariticleResponse.author=jsonObject.getString("author")
//            ariticleResponse.chapterId=jsonObject.getInt("chapterId")
//            ariticleResponse.chapterName=jsonObject.getString("chapterName")
//            ariticleResponse.collect=jsonObject.getBoolean("collect")
//            api.add(ariticleResponse)
//        }
//        Log.d("a3","api="+api.toString())
//        data=api
        return data
    }

//    fun AritrilList(pageNo:Int)= fire(Dispatchers.IO)
//    {
//        val aritrilListResponse=NetWork.getAritrilList(pageNo)
//        if (aritrilListResponse.isSucces())
//        {
//           val data= aritrilListResponse.getResponseData().datas
//            Result.success(data)
//        }else{
//            Result.failure(RuntimeException("response status is${aritrilListResponse.errorMsg}"))
//        }
//    }

    fun ProjecNewList(pageNo:Int)= fire(Dispatchers.IO)
    {
        val ProjecNewListResponse=NetWork.getProjecNewData(pageNo)
        if (ProjecNewListResponse.isSuccess())
        {
            val data= ProjecNewListResponse.getResponseData()

            Result.success(data)
        }else{
            Result.failure(RuntimeException("response status is${ProjecNewListResponse.errorMsg}"))
        }

    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
       liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e);
            }
            emit(result)
        }
}