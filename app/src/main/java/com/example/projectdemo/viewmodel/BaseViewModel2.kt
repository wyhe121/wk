package com.example.projectdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectdemo.EventLiveData

open class BaseViewModel2: ViewModel() {
    val loadingChange:UiLoadingChange by lazy { UiLoadingChange() }

            inner class UiLoadingChange{
                val showDialog by lazy{ EventLiveData<String>() }
                val dismissDialog by lazy { EventLiveData<Boolean>()}
            }
}