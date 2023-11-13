package com.quiet.onlytv.ui.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.quiet.onlytv.R
import com.quiet.onlytv.base.BaseAdapter

/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class HomeHorizontalAdapter(mutableList: MutableList<String>): BaseAdapter<String>(
    R.layout.rv_home_horizontal,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.itemView.setOnFocusChangeListener { view, b ->
            mSelectedListener?.onItemSelected(this,view,holder.bindingAdapterPosition,b)
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