package com.example.projectdemo.ui.adapter

import androidx.lifecycle.MutableLiveData
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.data.model.bean.BannerResponse
import com.example.projectdemo.data.model.bean.ListDataUiState
import com.example.projectdemo.ext.HttpRequestCoroutine
import com.example.projectdemo.ext.ResultState
import com.example.projectdemo.ext.apiService
import com.example.projectdemo.ext.request
import com.example.projectdemo.network.ApiService
import com.example.projectdemo.network.ApiService2
import com.example.projectdemo.viewmodel.BaseViewModel2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RequestHomeViewModel @Inject constructor
    (
    private val apiService2: ApiService2
):BaseViewModel2() {

    var pageNo=0
    var homeDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()
    var bannerData: MutableLiveData<ResultState<ArrayList<BannerResponse>>> = MutableLiveData()
    fun getHomeData(isRefresh:Boolean)
    {

        if (isRefresh)
        {
            pageNo=0
        }
        request({HttpRequestCoroutine.getHomeData(pageNo)},{
            pageNo++
            val listDataUiState=
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh &&it.isEmpty(),
                    listData = it.datas
                )
            homeDataState.value=listDataUiState
        },{
            val listDataUiState=
                ListDataUiState(
                    isSuccess = false,
                    errMessage=it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            homeDataState.value=listDataUiState
        })
    }
//    fun getBannerData() {
//        request({apiService.getBanner() }, bannerData)
//    }
}