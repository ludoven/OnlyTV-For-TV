package com.quiet.onlytv.ui.adapter

import android.R.attr.radius
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.quiet.onlytv.R
import com.quiet.onlytv.utils.OnKeyListener
import jp.wasabeef.glide.transformations.BlurTransformation


/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class CategoryAdapter(mutableList: MutableList<String>,private val onKeyListener: OnKeyListener) : BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.rv_category, mutableList
) {
    private val requestOptions: RequestOptions by lazy {
        RequestOptions().transform(BlurTransformation(40))
    }
    private val glide: RequestManager by lazy {
        Glide.with(context)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.title, item)
        glide
            .load(R.drawable.icon_banner)
            .apply(requestOptions)
            .into(holder.getView(R.id.img))

        holder.itemView.setOnFocusChangeListener { view, b ->
            if (b) {
                glide
                    .load(R.drawable.icon_banner)
                    .into(holder.getView(R.id.img))
            } else {
                glide
                    .load(R.drawable.icon_banner)
                    .apply(requestOptions)
                    .into(holder.getView(R.id.img))
            }
        }

        holder.itemView.setOnKeyListener { view, i, keyEvent ->
            return@setOnKeyListener onKeyListener.onKeyDown(holder.bindingAdapterPosition,i,keyEvent)
        }
    }

}