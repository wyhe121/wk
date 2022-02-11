package com.example.projectdemo.ext

import androidx.lifecycle.MutableLiveData
import com.example.projectdemo.data.model.bean.BaseResponse
import com.example.projectdemo.network.AppException
import com.example.projectdemo.network.ExceptionHandle

import java.lang.Error

 sealed  class  ResultState<out T>{

    companion object{
        fun <T> onAppSuccess(data:T):ResultState<T>{
            return Success(data)
        }

        fun <T> onAppLoading(loadingMessage:String):ResultState<T> =Loading(loadingMessage)
        fun <T> onAppError(error: AppException): ResultState<T> = Error(error)
    }
    data class Success<T>(val data:T):ResultState<T>(){
    }
    data class Loading(val loadingMessage:String):ResultState<Nothing>()
    data class Error(val error: AppException):ResultState<Nothing>()
}
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: BaseResponse<T>){
    value=when {
        result.isSuccess()->{
            ResultState.onAppSuccess(result.getResponseData())
        }
        else->{
            ResultState.onAppError(AppException(result.getResponseCode(),result.getResponseMsg()))
        }

    }
}

fun <T> MutableLiveData<ResultState<T>>.paresException(e:Throwable){
    this.value= ResultState.onAppError(ExceptionHandle.handleException(e))
}