package com.quiet.onlytv.ui.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.quiet.onlytv.R
import com.quiet.onlytv.base.BaseAdapter
import jp.wasabeef.glide.transformations.BlurTransformation


/**
 *
 *  author： ludoven
 *  date :   2023/11/8 17:32
 *
 */
class CategoryAdapter(mutableList: MutableList<String>) : BaseAdapter<String>(
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
            return@setOnKeyListener mKeyListener?.onKeyDown(holder.bindingAdapterPosition,i,keyEvent)?:false
        }
    }

}