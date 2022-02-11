package com.example.projectdemo.ui.adapter

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.projectdemo.App
import com.example.projectdemo.R
import com.example.projectdemo.appContext
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.ui.fragment.HomeFragment




class  HomeAdapter(data: MutableList<AriticleResponse>): BaseAdapter<BaseViewHolder,AriticleResponse>(data) {
    private val Ariticle = 1//文章类型

    private val Project = 2//项目类型 本来打算不区分文章和项目布局用统一布局的，但是布局完以后发现差异化蛮大的，所以还是分开吧

    private var showTag = false//是否展示标签 tag 一般主页才用的到

    private val arrayList=data
    init {
        setItemViewTypeName(Ariticle,R.layout.item_home)
        setItemViewTypeName(Project,R.layout.item_project)
    }

    override  fun convert(holder: BaseViewHolder, item: AriticleResponse) {

         when(holder.itemViewType)
         {
             Ariticle->{
                holder.setText(R.id.author_text,if (item.author.isNotEmpty()) item.author else item.shareUser)

             }
             Project->{
                 item.run {
                     holder.setText(R.id.author_text,if (author.isNotEmpty()) author else shareUser)
                     Glide.with(App.mApplication).load(envelopePic).transition(
                         DrawableTransitionOptions.withCrossFade(500)).into(holder.getView(R.id.project_imageview))

                 }

             }
         }
    }

    override fun getItemType(data: MutableList<AriticleResponse>, position: Int): Int {
        return if (TextUtils.isEmpty(data[position].envelopePic)) Ariticle else Project
    }


}