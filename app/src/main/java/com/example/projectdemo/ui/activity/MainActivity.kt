package com.example.projectdemo.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.projectdemo.App
import com.example.projectdemo.R
import com.example.projectdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.zip.Inflater
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {

        userDataBinding(true)

        super.onCreate(savedInstanceState)
    }


    override fun getLayoutID()= R.layout.activity_main

    override fun initView() {

    }

}