package com.example.projectdemo.weight.recyclerView

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.NonNull
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterWrapper() :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    fun AdapterWrapper(context: Context?, adapter: RecyclerView.Adapter<*>?) {
//        mAdapter = adapter
//    }
    private val mFootViews = SparseArrayCompat<View>()

    private val mHeaderViews = SparseArrayCompat<View>()

    private val BASE_ITEM_TYPE_HEADER = 100000

    private val BASE_ITEM_TYPE_FOOTER = 200000

    lateinit var mOnItemClickListener: OnItemClickListener

    lateinit var mOnItemLongClickListener: OnItemLongClickListener

    lateinit var mAdapter: RecyclerView.Adapter<*>

    interface OnItemClickListener{
            fun onItemClick(view:View,adapterPosition:Int)
    }

    constructor(context: Context,adapter: RecyclerView.Adapter<*>):this()
    {
        this.mAdapter = adapter
    }

    private fun getContentItemCount(): Int {
        return mAdapter.getItemCount()
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener)
    {
        mOnItemClickListener = itemClickListener
    }
    fun addFooterViewAndNotify(view:View)
    {
        addFooterView(view)
        notifyItemInserted(getHeaderCount() + getContentItemCount() + getFooterCount() - 1)
    }

    fun getFooterCount(): Int {
        return mFootViews.size()
    }

    fun getHeaderCount(): Int {
        return mHeaderViews.size()
    }

    fun addFooterView(view:View)
    {
        mFootViews.put(getFooterCount() + BASE_ITEM_TYPE_FOOTER,view)
    }

    override fun getItemCount(): Int {
        return getHeaderCount() + getContentItemCount() + getFooterCount()
    }
    fun isHeaderOrFooter(holder: RecyclerView.ViewHolder): Boolean {
        return if (holder is ViewHolder) true else isHeaderOrFooter(
            holder.adapterPosition
        )
    }
    fun isHeaderOrFooter(position: Int): Boolean {
        return isHeader(position) || isFooter(position)
    }
    interface OnItemLongClickListener{
          fun onItemLongClick(view: View,adapterPosition:Int)
    }

    fun isHeader(position: Int): Boolean {
        return position >= 0 && position < getHeaderCount()
    }

    fun isFooter(position: Int): Boolean {
        return position >= getHeaderCount() + getContentItemCount()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (isHeaderOrFooter(holder)) return
        val itemView = holder.itemView
        val mPosition= position-getHeaderCount()

        mAdapter.onBindViewHolder(holder as Nothing, position, payloads)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (isHeaderOrFooter(holder)) return
//
//        val itemView = holder.itemView
//        position -= getHeaderCount()
//        mAdapter.onBindViewHolder(holder, position, payloads)
    }
    internal class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var contentView = mHeaderViews[viewType]
        if (contentView != null) {
           return ViewHolder(contentView)
        }

        contentView = mFootViews[viewType]
        if (contentView != null) {
            return ViewHolder(contentView)
        }

        val viewHolder = mAdapter.onCreateViewHolder(parent, viewType)
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener { v ->
                mOnItemClickListener.onItemClick(
                    v,
                    viewHolder.adapterPosition
                )
            }
        }

        if (mOnItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener { v ->
                mOnItemLongClickListener.onItemLongClick(v, viewHolder.adapterPosition)
                true
            }
        }

        return viewHolder

    }

}