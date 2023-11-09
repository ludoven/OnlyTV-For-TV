package com.quiet.onlytv.ui.adapter

import android.view.KeyEvent
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.card.MaterialCardView
import com.quiet.onlytv.R
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener

/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class BannerAdapter(mutableList: MutableList<String>, private val listener: OnItemSelectedListener,private val keyListener: OnKeyListener): BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.rv_home_banner,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.title,"Shadow Hunter - ${holder.bindingAdapterPosition}")
        holder.setText(R.id.des,"A relentless detective unravels a web of secrets - ${holder.bindingAdapterPosition}")

        holder.itemView.setOnFocusChangeListener { view, b ->
            listener.onItemSelected(this,view,holder.bindingAdapterPosition,b)
        }

        holder.itemView.setOnKeyListener { view, i, keyEvent ->
            return@setOnKeyListener keyListener.onKeyDown(holder.bindingAdapterPosition,i,keyEvent)
        }
    }
}