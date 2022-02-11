package com.example.projectdemo.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdemo.weight.recyclerView.DefineLoadMoreView
import com.example.projectdemo.R
import com.example.projectdemo.databinding.FragmentHomeBinding
import com.example.projectdemo.ext.*
import com.example.projectdemo.ext.loadListData
import com.example.projectdemo.ui.adapter.HomeAdapter
import com.example.projectdemo.ui.adapter.RequestHomeViewModel
import com.example.projectdemo.viewmodel.HomeViewModel
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.include_recyclerview.*
import me.hgj.jetpackmvvm.demo.ui.adapter.AriticleAdapter

@AndroidEntryPoint
class HomeFragment2 : BaseFragment2<HomeViewModel,FragmentHomeBinding>() {
    //适配器
    private val articleAdapter: HomeAdapter by lazy { HomeAdapter(arrayListOf()) }

    private lateinit var footView: DefineLoadMoreView

    override fun layoutId() = R.layout.fragment_home

    private val requestHomeViewModel: RequestHomeViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        requestHomeViewModel.getHomeData(true)

        recyclerView.init2(LinearLayoutManager(context),articleAdapter).let {
            //因为首页要添加轮播图，所以我设置了firstNeedTop字段为false,即第一条数据不需要设置间距
        // it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))

            footView = it.initFooter2(SwipeRecyclerView.LoadMoreListener {
                Log.d("a3","loadMore")
             //   requestHomeViewModel.getHomeData(false)
            })

            //初始化FloatingActionButton
           // it.initFloatBtn(floatbtn)
        }
    }



    override fun initData() {
    }

    override fun createObserver() {
        requestHomeViewModel.run {
            homeDataState.observe(viewLifecycleOwner, Observer {
                loadListData2(it, articleAdapter, recyclerView, swipeRefresh)
            })
        }
    }
    }


