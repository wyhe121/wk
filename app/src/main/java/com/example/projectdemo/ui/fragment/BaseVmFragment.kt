package com.example.jetpackmvvm.base.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.projectdemo.ext.getVmClazz
import com.example.projectdemo.network.manager.NetState
import com.example.projectdemo.network.manager.NetworkStateManager
import com.example.projectdemo.viewmodel.BaseViewModel2


abstract class BaseVmFragment<VM: BaseViewModel2>:Fragment() {
    private val handler= Handler()

    private var isFirst:Boolean=true

    lateinit var mViewModel:VM

    lateinit var mActivity:AppCompatActivity

    abstract fun layoutId():Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(),container,false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity=context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst=true
        mViewModel=createViewModel()
        initView(savedInstanceState)
        createObserver()
        registorDefUIChange()
        initData()
    }

    open fun onNetworkStateChanged(netState: NetState){

    }

    private fun createViewModel():VM{
        return ViewModelProvider(this).get(getVmClazz(this))
    }


    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun lazyLoadData()

    abstract fun createObserver()

    override fun onResume() {
        super.onResume()
        onVisible()
    }
    open fun initData(){}
    abstract fun showLoading(message:String="请求网络中...")
    abstract fun dismissLoading()

    private fun registorDefUIChange()
    {
        mViewModel.loadingChange.showDialog.observeInFragment(this,{
            showLoading(it)
        })
        mViewModel.loadingChange.dismissDialog.observeInFragment(this,{
            dismissLoading()
        })
    }
    protected fun addLoadingObserve(vararg viewModels:BaseViewModel2)
    {
         viewModels.forEach { viewModel->
             viewModel.loadingChange.showDialog.observeInFragment(this,{
                 showLoading(it)
             })
             viewModel.loadingChange.dismissDialog.observeInFragment(this,{
                 dismissLoading()
             })

         }
    }
    private fun onVisible()
    {
        if (lifecycle.currentState== Lifecycle.State.STARTED&&isFirst){
            handler.postDelayed({
                lazyLoadData()
                NetworkStateManager.instance.mNetworkStateCallback.observeInFragment(
                    this,
                    {
                        if(!isFirst){
                    onNetworkStateChanged(it)
                } })
                isFirst = false

            },lazyLoadTime())
        }
    }
    open fun lazyLoadTime():Long{
        return 300
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}