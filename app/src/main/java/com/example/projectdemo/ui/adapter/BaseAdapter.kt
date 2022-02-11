package com.example.projectdemo.ui.adapter

import android.text.TextUtils
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdemo.R
import com.example.projectdemo.data.model.bean.AriticleResponse

abstract class BaseAdapter<VH:BaseViewHolder,T>(data:  MutableList<T>): RecyclerView.Adapter<VH>() {
    lateinit var VIEW_FOOTER:View
    private var setOnItemClickListener :OnItemClickListener?=null
    val typeViewMap: MutableMap<Int, Int> = mutableMapOf()

    var data: MutableList<T> = data ?: arrayListOf()
        internal set

    open fun getItem(@IntRange(from = 0) position: Int): T {
        return data[position]
    }
   fun setList(list: MutableList<T>)
   {
       data.addAll(list)
       notifyDataSetChanged()
   }
   fun setOnItemClickListener(setOnItemClickListener:OnItemClickListener)
   {
       this.setOnItemClickListener=setOnItemClickListener

   }
    override fun getItemViewType(position: Int): Int {

        return getItemType(data, position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val itemTypeLayout: Int

        itemTypeLayout = typeViewMap.get(viewType)!!

       val viewHolder= BaseViewHolder(LayoutInflater(parent,itemTypeLayout)) as VH


        return viewHolder //else return BaseViewHolder(LayoutInflater(parent,R.layout.item_home)) as VH

    }

    fun LayoutInflater(parent: ViewGroup,@LayoutRes itemTypeLayout:Int):View
    {
      return  LayoutInflater.from(parent.context).inflate(itemTypeLayout,parent,false)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convert(holder, getItem(position))
        holder.view.setOnClickListener {
            setOnItemClickListener!!.onItemClick(position)
        }


    }

    override fun getItemCount() = data.size

    abstract  fun convert(holder: VH, item: T)

    abstract fun getItemType(data: MutableList<T>,position: Int):Int


    fun setItemViewTypeName( typeName:Int,@LayoutRes typeLayout: Int)
    {
          typeViewMap.put(typeName,typeLayout)
    }

    fun addFooterView(footerView:View)
    {
        val params=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        footerView.layoutParams=params
        VIEW_FOOTER=footerView
        notifyItemInserted(getItemCount() - 1);
    }


    interface OnItemClickListener
    {
        fun onItemClick( position:Int )
    }

}
