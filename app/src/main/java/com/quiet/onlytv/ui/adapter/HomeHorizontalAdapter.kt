package com.quiet.onlytv.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.quiet.onlytv.R
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener

/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class HomeHorizontalAdapter(mutableList: MutableList<String>, private val listener: OnItemSelectedListener): BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.rv_home_horizontal,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {
//        holder.getView<ShapeableImageView>(R.id.img).

        holder.itemView.setOnFocusChangeListener { view, b ->
            listener.onItemSelected(this,view,holder.bindingAdapterPosition,b)
            holder.setVisible(R.id.title,b)
            if (b){
                holder.getView<ShapeableImageView>(R.id.img).strokeColor = ColorStateList.valueOf(ContextCompat.getColor(context,R.color.white))
            }else{
                holder.getView<ShapeableImageView>(R.id.img).strokeColor = ColorStateList.valueOf(ContextCompat.getColor(context,
                    com.ludoven.base.R.color.transparent))
            }
        }

    }
}