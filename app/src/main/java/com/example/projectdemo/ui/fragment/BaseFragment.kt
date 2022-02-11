package com.example.projectdemo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectdemo.ext.getVmClazz
import com.example.projectdemo.viewmodel.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel,DB:ViewDataBinding>:Fragment() {
    lateinit  var mDatabind:DB
    lateinit var mActivity: AppCompatActivity
    lateinit var mViewModel:VM
    //protected abstract val viewModelConfig: ViewModelConfig
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     //   val config = viewModelConfig
        mDatabind=DataBindingUtil.inflate(inflater,layoutId(),container,false)
        mDatabind.lifecycleOwner=this
     //   val variableId = config.getVmVariableId()

        return mDatabind.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity=context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createObserver()

        mViewModel=createViewModel()

        initData()

        initView(savedInstanceState)

    }
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun layoutId():Int

    abstract fun initData()

    abstract fun createObserver()

}