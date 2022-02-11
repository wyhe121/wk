package com.example.projectdemo.ui.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.jetpackmvvm.base.fragment.BaseVmDbFragment
import com.example.projectdemo.viewmodel.BaseViewModel2

abstract class BaseFragment2<VM: BaseViewModel2,DB:ViewDataBinding>:BaseVmDbFragment<VM,DB>() {
 abstract override fun layoutId(): Int

 abstract override fun initView(savedInstanceState: Bundle?)

 override fun lazyLoadData() {}

 override fun createObserver() {

 }

 override fun initData() {

 }

 override fun showLoading(message: String) {
 }

 override fun dismissLoading() {
 }

 override fun onPause() {
  super.onPause()
  //hideSoftKeyboard(activity)
 }

 override fun lazyLoadTime(): Long {
  return 300
 }
}