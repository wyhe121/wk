package com.example.projectdemo.ui.fragment

import android.os.Bundle
import com.example.projectdemo.R
import com.example.projectdemo.databinding.FragmentProjectBinding
import com.example.projectdemo.viewmodel.SquareViewModel

class SquareFragment:BaseFragment<SquareViewModel,FragmentProjectBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun layoutId()= R.layout.fragment_square
    override fun initData() {

    }

    override fun createObserver() {

    }
}