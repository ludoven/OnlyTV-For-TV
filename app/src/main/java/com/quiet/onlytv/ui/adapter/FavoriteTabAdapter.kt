package com.quiet.onlytv.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.quiet.onlytv.R
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener

/**
 *
 *  author： ludoven
 *  date :   2023/11/10 16:12
 *
 */
class FavoriteTabAdapter(
    mutableList: MutableList<String>
) : BaseAdapter<String>(
    R.layout.rv_favorite_tab, mutableList
) {

    private var selectIndex = 0

    fun setSelectIndex(index:Int){
        if (selectIndex == index){
            return
        }
        val lastIndex = selectIndex
        selectIndex = index
        notifyItemChanged(lastIndex)
        notifyItemChanged(selectIndex)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        super.convert(holder, item)
        holder.setText(R.id.title, item)

        holder.setGone(R.id.img,selectIndex != holder.bindingAdapterPosition)
    }

}