package com.example.projectdemo.viewmodel

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectdemo.EventLiveData
import com.example.projectdemo.data.model.bean.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher

open class BaseViewModel : ViewModel() ,LifecycleObserver {

    val uiChange: UIChange by lazy { UIChange() }
    override fun onCleared() {
       viewModelScope.cancel()
    }
    fun <T> launchFlow(
        request:suspend CoroutineScope.() -> ApiResponse<T>,
        resp:(T?)-> Unit,
        error: (String) -> Unit = {},
        loadMoreError: () -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        isStatueLayout: Boolean = false,
        isLoadMore: Boolean = false
        ){
        viewModelScope.launch {
            flow {
                emit(request())
            }.flowOn(Dispatchers.IO) .catch { t: Throwable ->   //异常捕获处理
                catch(error, t, isLoadMore, loadMoreError, isStatueLayout)
                end(isShowDialog, complete)
            }.collect {
           handlerCode(it,isStatueLayout,resp,error)
            }
        }
    }
    private fun catch(
        error: (String) -> Unit,
        t: Throwable,
        isLoadMore: Boolean,
        loadMoreError: () -> Unit,
        isStatueLayout: Boolean
    ) {
        error(t.message ?: "")   //有基类自行处理,业务层也可以自行处理
        if (isLoadMore) {   //isLoadMore 为true 表示数据已经加载第二页数据 则不需要显示statueError
            loadMoreError()  //交由业务层自行处理
        } else {
            if (isStatueLayout) {
                uiChange.statueError.call()   //显示数据加载失败界面
            }
            uiChange.msgEvent.postValue(t.message ?: "")
        }
    }
    private fun end(isShowDialog: Boolean, complete: () -> Unit) {
        if (isShowDialog) {
            uiChange.dismissDialog.call()
        }
        complete()
    }

    private fun <T> handlerCode(
        it: ApiResponse<T>,
        isStatueLayout: Boolean,
        resp: (T?) -> Unit,
        error: (String) -> Unit

    ) {
//       when(it.isSucces())
//       {
//             true ->{
//                 if (isStatueLayout) {
//                     uiChange.statueSuccess.call()
//                 }
                 resp(it.data)
//             }
//           else -> {
//               if (isStatueLayout) {
//                   uiChange.statueError.call()
//               }
//               error(it.errorCode ?: "")
//               uiChange.msgEvent.postValue(it.errorMsg)
//           }
      // }

    }
inner class UIChange
{
        val showDialog by lazy { ViewModelEvent<String>() }    //显示加载框
        val dismissDialog by lazy { ViewModelEvent<Void>() }     //关闭加载框
        val msgEvent by lazy { ViewModelEvent<String>() }    //发送消息
        val statueShowLoading by lazy { ViewModelEvent<Void>() } // 显示加载布局
        val statueSuccess by lazy { ViewModelEvent<Void>() }    //加载完成
        val statueError by lazy { ViewModelEvent<Void>() }     //加载错误，初次加载数据失败显示,后续如有adapter显示加载失败

}
}