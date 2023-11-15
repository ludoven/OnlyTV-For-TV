package com.quiet.onlytv.utils

import android.view.KeyEvent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter

interface OnItemSelectedListener {
    fun onItemSelected(adapter: BaseQuickAdapter<*, *>?, v: View?, pos: Int, hasFocus: Boolean)
}

interface OnKeyListener {
    fun onAdapterKeyEvent(pos:Int, keyCode: Int, event: KeyEvent?):Boolean
}