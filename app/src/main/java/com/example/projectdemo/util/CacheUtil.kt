package com.example.projectdemo.util

import android.text.TextUtils
import android.util.Log


object CacheUtil {
//    fun getUser():UserInfo?{
//        val kv=MMKV.mmkvWithID("app")
//        val userStr=kv.decodeString("user")
//        return if (TextUtils.isEmpty(userStr))
//        {
//            null
//        }else{
//            Gson().fromJson(userStr,UserInfo::class.java)
//        }
//    }
//    fun isFirst():Boolean{
//        Log.d("test","isFirst")
//        val kv=MMKV.mmkvWithID("app")
//        Log.d("test","kv.decodeBool="+kv.decodeBool("first",true))
//        return kv.decodeBool("first",true)
//    }
//    fun setFirst(first:Boolean):Boolean{
//        Log.d("test","setFirst")
//        val kv=MMKV.mmkvWithID("app")
//        return kv.encode("first",first)
//    }
//    fun isLogin():Boolean{
//        val kv=MMKV.mmkvWithID("app")
//        return kv.decodeBool("login",false)
//    }
//    fun setIsLogin(isLogin:Boolean)
//    {
//        val kv=MMKV.mmkvWithID("app")
//        kv.encode("login",isLogin)
//    }
//    fun setUser(userResponse:UserInfo?)
//    {
//        val kv=MMKV.mmkvWithID("app")
//        if (userResponse==null)
//        {
//            kv.encode("user","")
//            setIsLogin(false)
//
//        }
//    }
//    fun isNeedTop():Boolean{
//        val kv=MMKV.mmkvWithID("app")
//        return kv.decodeBool("top",true)
//    }
}