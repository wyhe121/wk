package com.example.projectdemo.ui.adapter

import android.app.Activity
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.projectdemo.R
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class ProjectAdapter(activity: FragmentActivity?, data:MutableList<String>):BaseAdapter<BaseViewHolder,String>(data) {

    val mActivity=activity

    val viedoItem=0
    init {
        setItemViewTypeName(viedoItem, R.layout.item_projectviedo)
    }
    override  fun convert(holder: BaseViewHolder, item: String) {

          var videoPlayer=   holder.getView<StandardGSYVideoPlayer>(R.id.video_player)
           videoPlayer.run {
               this.setUp(item,true,"摄像头")
               this.getTitleTextView().setVisibility(View.VISIBLE)
               this.getBackButton().setVisibility(View.VISIBLE)
               var orientationUtil = OrientationUtils(mActivity, this)
               this.fullscreenButton.setOnClickListener(View.OnClickListener { V:View?-> orientationUtil.resolveByClick() })
               this.setIsTouchWiget(true)
               this.getBackButton().setOnClickListener(View.OnClickListener { v: View? -> mActivity!!.onBackPressed() })
               this.startPlayLogic()
           }
    }

    override fun getItemType(data: MutableList<String>, position: Int)=0

}