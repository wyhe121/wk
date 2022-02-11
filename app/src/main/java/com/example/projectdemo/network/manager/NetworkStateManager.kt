package com.example.projectdemo.network.manager

import com.example.projectdemo.EventLiveData

class NetworkStateManager private constructor(){
    val mNetworkStateCallback= EventLiveData<NetState>()
    companion object{
        val instance:NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED)
        {
            NetworkStateManager()
        }
    }
}