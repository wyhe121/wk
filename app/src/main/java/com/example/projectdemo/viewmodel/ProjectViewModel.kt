package com.example.projectdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.projectdemo.network.Respository
import java.security.KeyStore

class ProjectViewModel :BaseViewModel() {

    private val pageNo= MutableLiveData<Int>()

     val loadAritrilData = Transformations.switchMap(pageNo){
        Respository.ProjecNewList(it)
    }
    fun LoadProjectPage(page:Int)
    {
        pageNo.value=page
    }
}