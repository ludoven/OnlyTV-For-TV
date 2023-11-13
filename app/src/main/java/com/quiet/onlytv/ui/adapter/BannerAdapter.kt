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
class BannerAdapter(mutableList: MutableList<String>): BaseAdapter<String>(
    R.layout.rv_home_banner,mutableList
) {
    override fun convert(holder: BaseViewHolder, item: String) {
        super.convert(holder, item)
        holder.setText(R.id.title,"Shadow Hunter - ${holder.bindingAdapterPosition}")
        holder.setText(R.id.des,"A relentless detective unravels a web of secrets - ${holder.bindingAdapterPosition}")
    }
}