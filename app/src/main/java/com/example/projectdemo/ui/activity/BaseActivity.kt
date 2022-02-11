package com.example.projectdemo.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.projectdemo.R

abstract  class BaseActivity<DB:ViewDataBinding>:AppCompatActivity(){

      var isUserDb=false

     lateinit var mDataBind:DB



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("isUserDb","onCreate")
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)

        window.statusBarColor=resources.getColor(R.color.colorPrimary)

        if (!isUserDb)
        {
            Log.d("isUserDb","NoUserDb")
            setContentView(getLayoutID())

        } else {
            Log.d("isUserDb","isUserDb")
            initDataBind()
        }

        initView()
    }
   // abstract fun<T:ViewDataBinding> getDataBinding():T

    open fun userDataBinding(isUserDb:Boolean)
    {
       this.isUserDb=isUserDb

    }

     fun  initDataBind() {
       mDataBind = DataBindingUtil.setContentView(this,getLayoutID())
    }

    abstract fun getLayoutID():Int

    abstract fun initView()
}