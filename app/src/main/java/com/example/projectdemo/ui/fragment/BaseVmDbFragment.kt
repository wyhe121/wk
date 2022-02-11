package com.example.jetpackmvvm.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.projectdemo.viewmodel.BaseViewModel2

abstract class BaseVmDbFragment<VM: BaseViewModel2,DB:ViewDataBinding>:BaseVmFragment<VM>() {
    lateinit var mDatabind:DB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabind=DataBindingUtil.inflate(inflater,layoutId(),container,false)
        mDatabind.lifecycleOwner=this
        return mDatabind.root
    }
}