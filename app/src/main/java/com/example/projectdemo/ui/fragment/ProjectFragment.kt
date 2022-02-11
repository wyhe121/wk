package com.example.projectdemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectdemo.R
import com.example.projectdemo.databinding.FragmentProjectBinding
import com.example.projectdemo.ext.bindViewPager
import com.example.projectdemo.ext.init
import com.example.projectdemo.ext.initMain
import com.example.projectdemo.ext.nav
import com.example.projectdemo.ui.adapter.BaseAdapter
import com.example.projectdemo.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_viewpager.*

class ProjectFragment:BaseFragment<ProjectViewModel,FragmentProjectBinding>() {


    val fragmentList:MutableList<Fragment> = arrayListOf()

    val titleArray:MutableList<String> = arrayListOf()

    override fun layoutId()= R.layout.fragment_project

    override fun initView(savedInstanceState: Bundle?) {

        view_pager.init(this,fragmentList,true)
        magic_indicator.bindViewPager(view_pager, titleArray )

    }



    override fun initData() {
        for (i in 0..10) {

            fragmentList.add(ProjectFragmentChildren())
            titleArray.add("$i")
        }
    }

    override fun createObserver() {

    }
}