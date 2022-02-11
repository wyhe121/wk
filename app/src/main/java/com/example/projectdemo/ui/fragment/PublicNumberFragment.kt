package com.example.projectdemo.ui.fragment

import android.os.Bundle
import com.example.projectdemo.R
import com.example.projectdemo.databinding.FragmentProjectBinding
import com.example.projectdemo.viewmodel.PublicNumberViewModel

class PublicNumberFragment:BaseFragment<PublicNumberViewModel,FragmentProjectBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun layoutId()= R.layout.fragment_publicnumber
    override fun initData() {

    }

    override fun createObserver() {

    }
}