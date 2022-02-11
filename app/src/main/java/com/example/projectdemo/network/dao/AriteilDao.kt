package com.example.projectdemo.network.dao

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.projectdemo.App
import com.example.projectdemo.App.Companion.mApplication
import com.example.projectdemo.data.model.bean.ApiPagerResponse
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.data.model.bean.ParameterizedTypeImpl
import com.example.projectdemo.ext.dataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object   AriteilDao {

    suspend fun savePlace(apiPagerResponse: ArrayList<AriticleResponse>){
        App.mApplication.dataStore.edit { settings ->
            settings[stringPreferencesKey("place")] = Gson().toJson(apiPagerResponse)
        }
    }

    suspend fun isSavePlace():Boolean{
      return  App.mApplication.dataStore.edit {
             it.contains(stringPreferencesKey("place"))
        }.contains(stringPreferencesKey("place"))

    }

    suspend fun getPlace():ArrayList<AriticleResponse>{
        return App.mApplication?.dataStore?.data.map{preferences ->
            val a = preferences[stringPreferencesKey("place")]
            val turnsType = object : TypeToken<ArrayList<AriticleResponse>>() {}.type
            Gson().fromJson<ArrayList<AriticleResponse>>(a,turnsType ) as ArrayList<AriticleResponse>
        }.first()
    }

     fun<T> savePlace2(place: ApiPagerResponse<ArrayList<AriticleResponse>>){
       sharenPreferences().edit().putString("place",Gson().toJson(place)).commit()
    }
    fun <T> getSavedPlace():T{

        val placeJson= sharenPreferences().getString("place","")
        val data = Gson().fromJson(placeJson, ApiPagerResponse::class.java)
//       val ar: ArrayList<AriticleResponse> = ArrayList<AriticleResponse>()
//
//      val api:ApiPagerResponse<ArrayList<AriticleResponse>> ? = ApiPagerResponse()
//        val type: Type =  api!!.javaClass.genericSuperclass
       // val type: Type = javaClass.genericSuperclass
       // if(type is ParameterizedType) {
         //   val types: Array<Type> = (type as ParameterizedType).getActualTypeArguments()

         //   val ty: Type = ParameterizedTypeImpl(ApiPagerResponse::class.java, arrayOf(types[0]))
          //
          //  return data as ApiPagerResponse<ArrayList<AriticleResponse>>
       // }


//        Log.d("a3","data.curPage="+data.curPage+","+data.curPage.javaClass.toString())
//        Log.d("a3","data.offset="+data.offset+","+data.offset.javaClass.toString())
//        Log.d("a3","data.over="+data.over+","+data.over.javaClass.toString())
//        Log.d("a3","data.size="+data.size+","+data.size.javaClass.toString())
//        Log.d("a3","data.datas="+data.datas.toString())
        return data as T
    }

    private fun sharenPreferences()=mApplication.getSharedPreferences("sunny_weather",
        Context.MODE_PRIVATE)
}