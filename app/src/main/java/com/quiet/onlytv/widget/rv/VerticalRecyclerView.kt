package com.quiet.onlytv.widget.rv

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.quiet.onlytv.widget.rv.BaseRecyclerView
import kotlin.math.roundToInt

/**
 *
 *  author： ludoven
 *  date :   2022/10/12 16:57
 *
 */
open class VerticalRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseRecyclerView(context, attrs) {


    init {
        canFocusOutVertical(false)
        setEnableIntercept(true)
        intervalsTime = 50
    }


    /**
     * 使指定的View快速滑动到中间
     * @param focused
     */
    fun scrollToCenterView(focused: View) {
        val distance = getNeedScrollDistance(focused)
        scrollViewVertical(distance)
    }

    /**
     * 使指定的View平稳滑动到中间
     * @param focused
     * @param duration
     */
    fun smoothScrollToCenterView(focused: View, duration: Int) {
        val distance = getNeedScrollDistance(focused)
        scrollViewVertical(distance, LinearInterpolator(), duration)
    }

    fun smoothScrollToCenterView(view: View, duration: Int, scaleHeight: Float) {
        val distance: Int
        val size = contentSize
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        distance = (view.top + scaleHeight / 2.0f - size / 2.0f).roundToInt()
        scrollViewVertical(distance, LinearInterpolator(), duration)
    }

    /**
     * 快速垂直滑动指定的距离
     * @param scrollDistance
     */
    private fun scrollViewVertical(scrollDistance: Int) {
        scrollBy(0, scrollDistance)
    }

    private fun scrollViewVertical(
        scrollDistance: Int,
        interpolator: Interpolator?,
        duration: Int
    ) {
        smoothScrollBy(0, scrollDistance, interpolator, duration)
    }

    /**
     * 获取需要滑动的距离
     * @param view
     */
    private fun getNeedScrollDistance(view: View?): Int {
        return getCenterDistance(view)
    }

    /**
     * 获取指定view滑动到中间位置所需滑动的距离
     * @param view
     * @return
     */
    protected open fun getCenterDistance(view: View?): Int {
        var distance = 0
        if (view == null) return distance
        val size = contentSize
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        distance = (view.top + view.height / 2.0f - size / 2.0f).roundToInt()
        return distance
    }

    /**
     * 获取当前控件的内容所占的总高度/宽度
     * @return
     */
    protected open val contentSize: Int
        get() = height - paddingBottom - paddingTop

}