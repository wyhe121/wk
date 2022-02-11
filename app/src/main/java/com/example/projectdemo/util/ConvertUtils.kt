package com.example.projectdemo.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import com.example.projectdemo.R

object ConvertUtils {
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}