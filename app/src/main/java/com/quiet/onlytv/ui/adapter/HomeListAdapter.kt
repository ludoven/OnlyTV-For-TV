package com.quiet.onlytv.ui.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.quiet.onlytv.R
import com.quiet.onlytv.base.BaseAdapter
import com.quiet.onlytv.widget.rv.HorizontalRecyclerView

/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class HomeListAdapter(mutableList: MutableList<String>): BaseAdapter<String>(
    R.layout.rv_home_list,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {
        val horizontalRv = holder.getView<HorizontalRecyclerView>(R.id.horizontal_rv)
        val adapter = HomeHorizontalAdapter(mutableListOf("","","","","","","","",""))
        horizontalRv.adapter = adapter
        horizontalRv.canFocusOutHorizontal(false)
    }
}