package com.quiet.onlytv.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener

/**
 *
 *  author： ludoven
 *  date :   2023/11/10 16:16
 *
 */
open class BaseAdapter <T>(
    layoutResId: Int,
    data: MutableList<T>? = null,
) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data) {

    open var mSelectedListener:OnItemSelectedListener ?=null
    open var mKeyListener:OnKeyListener ?=null

    fun setSelectedListener(listener: OnItemSelectedListener){
        mSelectedListener = listener
    }

    fun setKeyListener(listener: OnKeyListener){
        mKeyListener = listener
    }



    override fun convert(holder: BaseViewHolder, item: T) {
        holder.itemView.setOnFocusChangeListener { _, hasFocus ->
            mSelectedListener?.onItemSelected(this, holder.itemView, holder.bindingAdapterPosition, hasFocus)
        }
        holder.itemView.setOnKeyListener { _, keyCode, event ->
           return@setOnKeyListener mKeyListener?.onKeyDown(holder.bindingAdapterPosition, keyCode, event)?:false
        }
    }

}