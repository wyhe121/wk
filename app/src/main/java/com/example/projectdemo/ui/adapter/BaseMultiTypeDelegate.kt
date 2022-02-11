package com.example.projectdemo.ui.adapter

import android.util.SparseIntArray
import androidx.annotation.LayoutRes

abstract class BaseMultiTypeDelegate<T>(private var layouts: SparseIntArray = SparseIntArray()) {

    abstract fun getItemType(data: List<T>, position: Int): Int

    private fun registerItemType(type:Int,@LayoutRes layoutResId:Int){
        this.layouts.put(type,layoutResId)
    }

    fun addItemType(type:Int,@LayoutRes layoutResId:Int):BaseMultiTypeDelegate<T>{
        registerItemType(type,layoutResId)
        return this
    }
}