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
class HistoryAdapter(mutableList: MutableList<String>): BaseAdapter<String>(
    R.layout.rv_history,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.root_view,item)
    }
}