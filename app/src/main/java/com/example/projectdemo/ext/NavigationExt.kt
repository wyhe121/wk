package com.example.projectdemo.ext

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.nav(): NavController {
    return NavHostFragment.findNavController(this)
}

fun NavController.navigateActoin(resId:Int,bundle:Bundle,interval: Long = 500L)
{
    var currenTime=System.currentTimeMillis()
    var lastTime=0L
if (currenTime-lastTime>interval)
{
    lastTime=currenTime
    this.navigate(resId,bundle)

}


}