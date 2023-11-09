package com.quiet.onlytv.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.quiet.onlytv.R
import com.quiet.onlytv.utils.OnItemSelectedListener

/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class HomeTabAdapter(mutableList: MutableList<String>,private val listener: OnItemSelectedListener): BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.rv_home_tab,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.text,item)
        holder.itemView.setOnFocusChangeListener { view, b ->
            listener.onItemSelected(this,view,holder.bindingAdapterPosition,b)
        }
    }
}