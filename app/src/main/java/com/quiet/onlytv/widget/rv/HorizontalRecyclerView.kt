package com.quiet.onlytv.widget.rv

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.math.roundToInt


open class HorizontalRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseRecyclerView(context, attrs) {

    init {
        canFocusOutHorizontal(false)
        setEnableIntercept(true)
        intervalsTime = 50
    }


    fun scrollToCenterView(position: Int) {
        if (position < 0) return
        val manager = layoutManager
        val view = manager!!.findViewByPosition(position)
        view?.let { scrollToCenterView(it) }
    }

    /**
     * 使指定的View滑动到中间
     * @param focused
     */
    fun scrollToCenterView(focused: View) {
        val distance = getNeedScrollDistance(focused)
        scrollViewHorizontal(distance)
    }

    /**
     * 快速垂直滑动指定的距离
     *
     * @param scrollDistance
     */
    private fun scrollViewHorizontal(scrollDistance: Int) {
        smoothScrollBy(scrollDistance, 0)
    }

    fun scrollToCenterView(focused: View, duration: Int) {
        val distance = getNeedScrollDistance(focused)
        scrollViewHorizontal(distance, duration)
    }

    private fun scrollViewHorizontal(scrollDistance: Int, duration: Int) {
        smoothScrollBy(scrollDistance, 0, AccelerateDecelerateInterpolator(), duration)
    }

    /**
     * 获取需要滑动的距离
     *
     * @param view
     */
    private fun getNeedScrollDistance(view: View): Int {
        return getCenterDistance(view)
    }

    /**
     * 获取指定view滑动到中间位置所需滑动的距离
     *
     * @param view
     * @return
     */
    private fun getCenterDistance(view: View): Int {
        var distance = 0
        val size = contentSize
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        distance = (view.left + view.width / 2.0f - size / 2.0f).roundToInt()
        return distance
    }

    /**
     * 获取当前控件的内容所占的总高度/宽度
     *
     * @return
     */
    protected open val contentSize: Int
        get() = width - paddingRight - paddingLeft


    /**
     * 是否已经滑动到最后
     *
     * @return
     */
    private val isScrolledEnd: Boolean
        get() {
            val layoutManager = layoutManager as LinearLayoutManager?
            val lastVisibleItemPosition = layoutManager!!.findLastVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            return visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1
        }

    private fun isLastItem(focusedView: View): Boolean {
        val position = getChildAdapterPosition(focusedView)
        val layoutManager = layoutManager as LinearLayoutManager?
        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager.itemCount
        return visibleItemCount > 0 && position == totalItemCount - 1
    }
}