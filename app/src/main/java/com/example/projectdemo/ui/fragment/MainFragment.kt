package com.example.projectdemo.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.projectdemo.R
import com.example.projectdemo.databinding.FragmentMainBinding
import com.example.projectdemo.ext.init
import com.example.projectdemo.ext.initMain
import com.example.projectdemo.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: BaseFragment<MainViewModel,FragmentMainBinding>() {
    override fun layoutId()= R.layout.fragment_main
    override fun initData() {

    }

    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
              main_viewPager.initMain(this)

              main_bottomNavation.init {
                     when(it)
                   {
                     R.id.menu_main ->  main_viewPager.setCurrentItem(0,false)
                     R.id.menu_project -> main_viewPager.setCurrentItem(1,false)
                     R.id.menu_system -> main_viewPager.setCurrentItem(2,false)
                     R.id.menu_public -> main_viewPager.setCurrentItem(3,false)
                     R.id.menu_me -> main_viewPager.setCurrentItem(4,false)
                   }
                  true
              }
    }



}