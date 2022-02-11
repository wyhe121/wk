package com.example.projectdemo.ui.fragment

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdemo.R
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.databinding.FragmentWebBinding
import com.example.projectdemo.ext.init
import com.example.projectdemo.ext.initClose
import com.example.projectdemo.ext.nav
import com.example.projectdemo.viewmodel.WebViewModel
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

class WebFragment:BaseFragment<WebViewModel,FragmentWebBinding>() {
    var mAgentWeb:AgentWeb?=null
    private var preWeb: AgentWeb.PreAgentWeb? = null


    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose(mViewModel.showTitle, R.menu.home_menu){
            mAgentWeb?.let {web->
                if (web.webCreator.webView.canGoBack()) {
                    web.webCreator.webView.goBack()
                } else {
                    nav().navigateUp()
                }
            }
        }

        preWeb = AgentWeb.with(this)
            .setAgentWebParent(webcontent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
        mAgentWeb = preWeb?.go(mViewModel.url)
    }

    override fun layoutId()= R.layout.fragment_web

    override fun initData() {
        arguments?.run {
            getParcelable<AriticleResponse>("ariticleData")?.let{
                mViewModel.url=it.link
                mViewModel.showTitle=it.title
            }

        }
    }

    override fun createObserver() {
    }
    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        mActivity.setSupportActionBar(null)
        super.onDestroy()
    }
}