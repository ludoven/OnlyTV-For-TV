package com.quiet.onlytv.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

object AnimationUtil {

    fun View.animateWithCallback(
        context: Context,
        animType: Int,
        time: Long,
        onStart: () -> Unit,
        onEnd: () -> Unit
    ) {
        //避免clearAnimation 时重复执行onAnimationEnd
        var hasExecutedOnEnd = false
        val animation = AnimFactory.getAnim(context, animType).apply {
            duration = time
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    onStart.invoke()
                }

                override fun onAnimationEnd(animation: Animation?) {
                    if (!hasExecutedOnEnd) {
                        onEnd.invoke()
                        hasExecutedOnEnd = true
                        clearAnimation(this@animateWithCallback)
                    }
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
        startAnimation(animation)
    }

    fun startRotationAnim(view: View, time: Long = 3000) {
        val rotationAnimation = RotateAnimation(
            0f, // 起始角度
            360f, // 结束角度
            Animation.RELATIVE_TO_SELF, // 旋转中心点相对 view 的 x 坐标
            0.5f, // 旋转中心点相对 view 的 y 坐标
            Animation.RELATIVE_TO_SELF, // 旋转中心点相对 view 的 x 坐标
            0.5f // 旋转中心点相对 view 的 y 坐标
        ).apply {
            duration = time
            repeatCount = Animation.INFINITE // 无限循环
            fillAfter = true
        }
        view.startAnimation(rotationAnimation)
    }

    fun clearAnimation(v: View?) {
        if (null == v) {
            return
        }
        if (null != v.animation) {
            v.animation.cancel()
            v.clearAnimation()
        }
    }
}