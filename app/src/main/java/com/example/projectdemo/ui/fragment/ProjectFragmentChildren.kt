package com.example.projectdemo.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdemo.App
import com.example.projectdemo.R
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.databinding.FragmentProjectBinding
import com.example.projectdemo.databinding.FragmentProjectchildrenBinding
import com.example.projectdemo.ext.bindViewPager
import com.example.projectdemo.ext.init
import com.example.projectdemo.ext.initMain
import com.example.projectdemo.ext.nav
import com.example.projectdemo.ui.adapter.BaseAdapter
import com.example.projectdemo.ui.adapter.HomeAdapter
import com.example.projectdemo.ui.adapter.ProjectAdapter
import com.example.projectdemo.viewmodel.ProjectViewModel
import com.example.projectdemo.weight.recyclerView.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_viewpager.*

class ProjectFragmentChildren:BaseFragment<ProjectViewModel,FragmentProjectchildrenBinding>() {

    var  mArray :MutableList<AriticleResponse> = arrayListOf()

    private val articleAdapter: HomeAdapter by lazy { HomeAdapter(mArray) }

    val projectViewModel:ProjectViewModel by viewModels()

    val fragmentList:MutableList<Fragment> = arrayListOf()

    val titleArray:MutableList<String> = arrayListOf()

    override fun layoutId()= R.layout.fragment_projectchildren

    override fun initView(savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(activity)

        recyclerView.init(articleAdapter,layoutManager)

    }

    override fun initData() {
        projectViewModel.LoadProjectPage(0)
    }

    override fun createObserver() {
        projectViewModel.loadAritrilData.observe(this,{
            var data= it.getOrNull()
            if (data!=null)
            {
                for (i in 0..data.datas!!.size-1) mArray.add(data.datas!!.get(i))

                articleAdapter.notifyDataSetChanged()

            }
        })
    }
}