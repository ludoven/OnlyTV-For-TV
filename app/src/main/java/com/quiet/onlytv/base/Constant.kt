package com.quiet.onlytv.base

import android.view.KeyEvent

object Constant {

    interface KeyCode{
        companion object{
            /** OK 键*/
            val selectKeys = intArrayOf(
                KeyEvent.KEYCODE_BUTTON_SELECT,
                KeyEvent.KEYCODE_BUTTON_A,
                KeyEvent.KEYCODE_ENTER,
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_NUMPAD_ENTER
            )

            /** 返回 键*/
            val backKeys = intArrayOf(
                KeyEvent.KEYCODE_B,
                KeyEvent.KEYCODE_BACK,
                KeyEvent.KEYCODE_ESCAPE
            )
        }
    }



    interface Anim {
        companion object {
            const val BOTTOM_ENTER = 1
            const val BOTTOM_EXIT = 2
            const val LEFT_ENTER = 3
            const val LEFT_EXIT = 4
            const val TOP_ENTER = 5
            const val TOP_EXIT = 6
            const val RIGHT_ENTER = 7
            const val RIGHT_EXIT = 8
            const val ALPHA_ENTER = 9
            const val ALPHA_EXIT = 10
        }
    }
}