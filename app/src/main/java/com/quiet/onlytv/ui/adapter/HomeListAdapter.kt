package com.quiet.onlytv.ui.adapter

import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.card.MaterialCardView
import com.quiet.onlytv.R
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener
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