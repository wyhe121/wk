package com.example.projectdemo.data.model.bean

import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.Serializable

/**
 *  分页数据的基类
 */
 data class ApiPagerResponse2<T>(
    var  datas: T?,
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
) : Serializable {
constructor():this(null,0,0,false,0,0,0)
    /**
     * 数据是否为空
     */
    fun isEmpty() = (datas as List<*>).size == 0

    /**
     * 是否为刷新
     */
    fun isRefresh() = offset == 0

    /**
     * 是否还有更多数据
     */
    fun hasMore() = !over
}