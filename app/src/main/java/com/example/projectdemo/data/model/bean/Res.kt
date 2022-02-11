package com.example.projectdemo.data.model.bean


/**
 * 混淆的时候 注意加上！！！！！！！！！！！！！！！
 */
data class Res<T>(
    var code: Int = 0,
    var msg: String = "",
    val data: T?
)