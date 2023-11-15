package com.quiet.onlytv.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatSeekBar

class MySeekBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatSeekBar(context, attrs) {

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (isEnabled) {
            var increment = 1
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_MINUS -> {
                    increment = -increment
                    //用于解决TV端存在二级进度时，在同时Progress和SecondaryProgress时，出现Thumb先滑到SecondaryProgress，
                    // 然后再回弹到Progress的界面Bug
                    progress += increment
                    return true
                }

                KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_PLUS, KeyEvent.KEYCODE_EQUALS -> {
                    progress += increment
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}