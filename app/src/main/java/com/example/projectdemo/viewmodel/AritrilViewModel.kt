package com.example.projectdemo.viewmodel

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.projectdemo.App.Companion.mApplication
import com.example.projectdemo.data.model.bean.*
import com.example.projectdemo.network.ApiService
import com.example.projectdemo.network.Respository
import com.example.projectdemo.network.dao.AriteilDao
import com.example.projectdemo.network.dao.UserPreferencesSerializer
import com.example.projectdemo.protobuf.UserPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
@HiltViewModel
class AritrilViewModel @Inject constructor
    (
    private val apiService: ApiService
): BaseViewModel() {
    var pageNumber=0
    val isRefreshing = MutableLiveData<Boolean>().also { it.value = false }

    val infoResult = MutableLiveData<ArrayList<AriticleResponse>>()
    val infoResult2 : MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()
    private val pageNo=MutableLiveData<Int>()

    private val flow1 = MutableStateFlow(0)

    var homeDataState: MutableLiveData<AriticleResponse> = MutableLiveData()

    var homeAriticleArray:ArrayList<AriticleResponse> = arrayListOf()

     suspend fun saveAritril(AriticleResponse:ArrayList<AriticleResponse>)
    {
        Respository.saveAritrilViewModel(AriticleResponse)
    }

    fun getInfoList(pageNumber: Int, loadMoreError: () -> Unit)
    {
        launchFlow(
        request = { apiService.getArticle(pageNumber)},
        resp = { infoResult.postValue(it!!.datas)},
        isLoadMore = (pageNumber != 1),
        loadMoreError = { loadMoreError() },
        complete = { isRefreshing.postValue(false) }
    )

    }
    fun getInfoList2( loadMoreError: () -> Unit)
    {
        launchFlow(
            request = { apiService.getArticle(pageNumber)},
            resp = {
                val listDataUiState=
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = true,
                    isEmpty  =it!!.isEmpty(),
                    hasMore = it.hasMore(),
                    listData = it.datas
                )
                infoResult2.postValue(listDataUiState)
                   },
            isLoadMore = (pageNumber != 1),
            loadMoreError = { loadMoreError() },
            complete = { isRefreshing.postValue(false) }
        )
        pageNumber++
        Log.d("getInfoList2","pageNumber="+pageNumber)
    }
    suspend fun isSaveAritril():Boolean
    {
        return Respository.isSave()
    }
    suspend fun getAritril():ArrayList<AriticleResponse>
    {
       val data=Respository.getAritrilViewModel() as ArrayList<AriticleResponse>
        return data
    }

//    val loadAritrilData = Transformations.switchMap(pageNo){
//        Respository.AritrilList(it)
//    }

    fun LoadAritrilPage(page:Int)
    {
        pageNo.value=page
    }
}