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

        val cardView = holder.getView<MaterialCardView>(R.id.root_view)
        holder.itemView.setOnFocusChangeListener { view, b ->
            listener.onItemSelected(this,view,holder.bindingAdapterPosition,b)
            if (b){
                cardView.strokeColor = ContextCompat.getColor(context,R.color.white)
            }else{
                cardView.strokeColor = ContextCompat.getColor(context, com.ludoven.base.R.color.white20)
            }
        }

        holder.itemView.setOnKeyListener { view, i, keyEvent ->
            return@setOnKeyListener keyListener.onKeyDown(holder.bindingAdapterPosition,i,keyEvent)
        }
    }
}