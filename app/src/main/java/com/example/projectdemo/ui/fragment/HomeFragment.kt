package com.example.projectdemo.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdemo.App.Companion.mApplication
import com.example.projectdemo.R
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.databinding.FragmentHomeBinding
import com.example.projectdemo.ext.*
import com.example.projectdemo.ext.initFooter
import com.example.projectdemo.ui.adapter.BaseAdapter
import com.example.projectdemo.ui.adapter.HomeAdapter
import com.example.projectdemo.util.ConvertUtils
import com.example.projectdemo.viewmodel.AritrilViewModel
import com.example.projectdemo.viewmodel.HomeViewModel
import com.example.projectdemo.viewmodel.HomeViewModel2
import com.example.projectdemo.weight.recyclerView.DefineLoadMoreView
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment:BaseFragment<HomeViewModel2,FragmentHomeBinding>(){
    //适配器
    //private val articleAdapter: AriticleAdapter by lazy { AriticleAdapter(arrayListOf(), true) }

    private lateinit var footView: DefineLoadMoreView

    val HomeAdapter: HomeAdapter by lazy{ HomeAdapter(arrayListOf())}

    val viewModel: AritrilViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.init("首页", R.menu.home_menu)
       // val layoutManager = LinearLayoutManager(activity)
         recyclerView.init2(LinearLayoutManager(context),HomeAdapter).let {

             footView =  it.initFooter2(SwipeRecyclerView.LoadMoreListener {
                            viewModel.getInfoList2(){}
             })
         }
        HomeAdapter.run {
         this.setOnItemClickListener(object :BaseAdapter.OnItemClickListener{
             override fun onItemClick(position: Int) {
                nav().navigate(R.id.action_to_webFragment,Bundle().apply {
                    putParcelable("ariticleData",HomeAdapter.data[position-this@HomeFragment.recyclerView.headerCount])
                })
             }

         })


        }

    }
    override fun initData() {
        viewModel.getInfoList2() {}
    }

    override fun createObserver() {
        viewModel.infoResult2.observe(this, Observer {
            loadListData(HomeAdapter,it,recyclerView)
        })
    }

    override fun layoutId()=R.layout.fragment_home
  //  override fun getLayoutId()
}