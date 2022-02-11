package com.example.projectdemo.data.model.bean

data class PageList<T>(
    val curPage:Int,
    val total:Int,
    val pageCount:Int,
    val list:MutableList<T>
)
