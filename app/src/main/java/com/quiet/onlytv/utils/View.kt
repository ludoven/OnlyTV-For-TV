package com.quiet.onlytv.utils

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


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

/**
 * 禁止ViewPager2内部类的RecycleView获得焦点
 */
fun ViewPager2.disablePagerInnerRvFocus() {
    for (i in 0 until childCount) {
        if (getChildAt(i) is RecyclerView) {
            getChildAt(i).isFocusable = false
        }
    }
}




