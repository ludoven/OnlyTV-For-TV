package com.quiet.onlytv.widget.rv

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView



open class BaseRecyclerView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RecyclerView(context, attrs, defStyleAttr) {


    init {
        //        descendantFocusability = FOCUS_AFTER_DESCENDANTS
//        isChildrenDrawingOrderEnabled = true
        itemAnimator = null

    }

    private var mHandler = Handler(Looper.getMainLooper())

    /**
     * 是否在暂停请求图片
     */
    private var isPausing = false

    /**
     *  是否启用 按键时图片暂停加载
     */
    var isOpenImgLoader = false

    /** 是否可以从上方移出*/
    var canFocusOutUp = true

    /** 是否可以从下方移出*/
    var canFocusOutDown = true

    /** 是否可以从右方移出*/
    var canFocusOutRight = true

    /** 是否可以左边移出*/
    var canFocusOutLeft = true


    //焦点移出recyclerview的事件监听
    private var listener: OnListener? = null

    private var previousKeyPressTime: Long = 0

    /**
     *  设置按键间隔时间  防止过快的按键
     */
    var intervalsTime = 100

    /**
     *  是否开启拦截按键过快事件
     */
    private var isEnableIntercept = true



    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (isEnableIntercept) {
            if (event?.action == KeyEvent.ACTION_DOWN) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - previousKeyPressTime < intervalsTime) {
                    return true
                }
                previousKeyPressTime = currentTime
            }
        }
        return super.dispatchKeyEvent(event)
    }


    //覆写focusSearch寻焦策略
    override fun focusSearch(focused: View?, direction: Int): View? {
        val view = super.focusSearch(focused, direction)
        if (focused == null) {
            return view
        }
        return if (view != null) {
            //该方法返回焦点view所在的父view,如果是在recyclerview之外，就会是null.所以根据是否是null,来判断是否是移出了recyclerview
            if (findContainingItemView(view) == null) {
                interceptFocus(view, focused, direction)
            } else {
                view
            }
        } else {
            interceptFocus(view, focused, direction)
        }
    }

    private fun interceptFocus(focusSearchView: View?, focused: View?, direction: Int): View? {
        if (!canFocusOutDown && direction == FOCUS_DOWN) return focused
        if (!canFocusOutLeft && direction == FOCUS_LEFT) return focused
        if (!canFocusOutUp && direction == FOCUS_UP) return focused
        if (!canFocusOutRight && direction == FOCUS_RIGHT) return focused

        //调用移出的监听
        if (listener != null) {
            return listener!!.onFocusLost(focusSearchView, focused, direction)
        }
        return focusSearchView
    }


    override fun requestChildFocus(child: View, focused: View) {
        if (!hasFocus()) {
            //recyclerview 子view 重新获取焦点，调用移入焦点的事件监听
            listener?.onFocusGain(child, focused)
        }
        super.requestChildFocus(child, focused) //执行过super.requestChildFocus之后hasFocus会变成true
    }

    /**
     * 控制当前焦点最后绘制，防止焦点放大后被遮挡
     * 原顺序123456789，当4是focus时，绘制顺序变为123567894
     */
    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        val focusedChild = focusedChild
        return if (focusedChild == null) {
            super.getChildDrawingOrder(childCount, i)
        } else {
            val index = indexOfChild(focusedChild)
            if (i == childCount - 1) {
                return index
            }
            if (i < index) {
                i
            } else i + 1
        }
    }

    interface OnListener {
        fun onFocusLost(focusSearch: View?, lastFocusChild: View?, direction: Int): View? {
            return focusSearch
        }

        fun onFocusGain(child: View?, focued: View?) {}
    }


    fun setOnFocusListener(onListener: OnListener) {
        listener = onListener
    }


    fun canFocusOut(canOut: Boolean) {
        this.canFocusOutLeft = canOut
        this.canFocusOutDown = canOut
        this.canFocusOutRight = canOut
        this.canFocusOutUp = canOut
    }

    fun canFocusOutVertical(canOut: Boolean) {
        this.canFocusOutDown = canOut
        this.canFocusOutUp = canOut
    }

    fun canFocusOutHorizontal(canOut: Boolean) {
        this.canFocusOutLeft = canOut
        this.canFocusOutRight = canOut
    }

    open fun setEnableIntercept(enable: Boolean) {
        isEnableIntercept = enable
    }
}
