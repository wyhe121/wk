package com.example.projectdemo.weight.recyclerView

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class SwipeRecyclerView(context: Context) : RecyclerView(context) {

    constructor(context: Context,attrs:AttributeSet):this(context)
    {
    }
 constructor(context: Context,attrs:AttributeSet,defStyleAttr:Int):this(context,attrs)
 {
 }
   private val mHeaderViewList: List<View> = ArrayList()

   private val mFooterViewList= arrayListOf<View>()

   private val mAdapterWrapper: AdapterWrapper = AdapterWrapper()

   fun addFooterView(view:View)
   {
        mFooterViewList.add(view)
      if (mAdapterWrapper != null) {
         mAdapterWrapper.addFooterViewAndNotify(view)
      }
   }


}