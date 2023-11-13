package com.quiet.onlytv.ui.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.quiet.onlytv.R
import com.quiet.onlytv.base.BaseAdapter

/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class HomeTabAdapter(mutableList: MutableList<String>): BaseAdapter<String>(
    R.layout.rv_home_tab,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {
        super.convert(holder, item)
        holder.setText(R.id.text,item)
    }
}