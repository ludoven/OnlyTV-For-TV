package com.quiet.onlytv.widget.rv

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.LinearSmoothScroller


class TopSmoothScroller(context: Context,private val millisecondsPerInch:Float = 25f) :
    LinearSmoothScroller(context) {


    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return millisecondsPerInch / displayMetrics.densityDpi
    }

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun onStop() {
        super.onStop()
        val targetPosition = targetPosition
        val targetView: View? = findViewByPosition(targetPosition)
        targetView?.let {
            if (!it.hasFocus()) it.requestFocus()
        }

    }
}