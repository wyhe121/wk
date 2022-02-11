package com.example.projectdemo.ui.fragment

import android.os.Bundle
import com.example.projectdemo.R
import com.example.projectdemo.databinding.FragmentMeBinding
import com.example.projectdemo.databinding.FragmentProjectBinding
import com.example.projectdemo.viewmodel.MeViewModel

class MeFragment:BaseFragment<MeViewModel,FragmentMeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }
    override fun layoutId()= R.layout.fragment_me
    override fun initData() {

    }

    override fun createObserver() {

    }
}