package com.example.projectdemo.data.model.bean

data class ListDataUiState<T>
    (
    val isSuccess:Boolean,
    val errMessage:String="",
    val isRefresh:Boolean=false,
    val isEmpty:Boolean=false,
    val hasMore:Boolean=false,
    val isFirstEmpty:Boolean=false,
    val listData:ArrayList<T> =arrayListOf()

    )


