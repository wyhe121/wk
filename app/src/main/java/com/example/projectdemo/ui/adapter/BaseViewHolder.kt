package com.example.projectdemo.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdemo.R


  class BaseViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    val view = view

    val itemMap = mutableMapOf<Int, View>()

    fun <T : View> getView(itemId: Int): T {
        if (!itemMap.containsKey(itemId)) {
            itemMap.put(itemId, itemId.findView())
        }
        return itemMap.get(itemId) as T
    }

    fun <T> Int.findView(): T {
        return view.findViewById(this) as T
    }

    fun setText(@IdRes itemId: Int, text: String) {
        getView<TextView>(itemId).text = text
    }
    fun setImageView(@IdRes itemId: Int, imageResource: Int) {
        getView<ImageView>(itemId).setImageResource(imageResource)
    }

}