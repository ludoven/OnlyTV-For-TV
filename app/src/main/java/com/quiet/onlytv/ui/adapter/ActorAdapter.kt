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
 *  date :   2023/11/14 15:32
 *
 */
class ActorAdapter(mutableList: MutableList<String>): BaseAdapter<String>(
    R.layout.rv_actor,mutableList
) {

    override fun convert(holder: BaseViewHolder, item: String) {

    }
}