package com.quiet.onlytv.utils

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.requestFocusPos(pos: Int): Boolean {
    val findViewByPosition =
        layoutManager?.findViewByPosition(pos)
            ?: getChildAt(0)
    if (findViewByPosition != null) {
        findViewByPosition.requestFocus()
        return true
    }
    return false
}

fun View.requestFocused() {
    this.requestFocus()
    this.requestFocusFromTouch()
}


fun ViewGroup.focusSearchEnable(enable: Boolean) {
    this.descendantFocusability =
        if (enable) ViewGroup.FOCUS_AFTER_DESCENDANTS else ViewGroup.FOCUS_BLOCK_DESCENDANTS
}

fun View.onGlobalLayout(callback: () -> Unit) {
    val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            callback()
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(listener)
}




