package com.quiet.onlytv.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import com.quiet.onlytv.App
import com.quiet.onlytv.R
import com.quiet.onlytv.base.Constant

object AnimFactory {
    fun getAnim(context: Context?, animType: Int): Animation {
        when (animType) {
            Constant.Anim.BOTTOM_ENTER -> return getAnimation(context,R.anim.anim_bottom_enter)
            Constant.Anim.BOTTOM_EXIT -> return getAnimation(context,R.anim.anim_bottom_exit)
            Constant.Anim.TOP_ENTER -> return getAnimation(context,R.anim.anim_top_enter)
            Constant.Anim.TOP_EXIT -> return getAnimation(context,R.anim.anim_top_exit)
            Constant.Anim.LEFT_ENTER -> return getAnimation(context,R.anim.anim_left_enter)
            Constant.Anim.LEFT_EXIT -> return getAnimation(context,R.anim.anim_left_exit)
            Constant.Anim.RIGHT_ENTER -> return getAnimation(context,R.anim.anim_right_enter)
            Constant.Anim.RIGHT_EXIT -> return getAnimation(context,R.anim.anim_right_exit)
            Constant.Anim.ALPHA_ENTER -> return getAnimation(context,R.anim.anim_alpha_enter)
            Constant.Anim.ALPHA_EXIT -> return getAnimation(context,R.anim.anim_alpha_exit)
        }
        return getAnimation(context,R.anim.anim_bottom_enter)
    }



    private fun getAnimation(context:Context?,@AnimRes id: Int): Animation {
        val animation = AnimationUtils.loadAnimation(context, id)
        animation.fillAfter = true
        return animation
    }
}