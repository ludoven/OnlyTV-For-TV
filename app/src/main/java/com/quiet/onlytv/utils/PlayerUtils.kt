package com.quiet.onlytv.utils

import java.util.Formatter
import java.util.Locale

/**
 *
 *  author： ludoven
 *  date :   2022/5/24 10:59
 *  播放器内用到的工具类
 */
object PlayerUtils {

    fun formatPlayTime(milliseconds: Long): String? {
        val totalSeconds = milliseconds / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        val stringBuilder = StringBuilder()
        val mFormatter = Formatter(stringBuilder, Locale.getDefault())
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }

    /**
     * 计算当前时间与总时间之间的分钟差。
     * @param currentTime 当前时间，单位为毫秒。
     * @param totalTime 总时间，单位为毫秒。
     * @return 当前时间与总时间之间的分钟差。
     */
    fun calculateMinute(currentTime: Long, totalTime: Long): Int {
        // 计算两个时间的毫秒差
        val millisDiff: Long = totalTime - currentTime
        // 将毫秒差转换成分钟差
        // 返回分钟差
        return (millisDiff / 1000.0 / 60.0).toInt()
    }


    /**
     * 计算当前时间与总时间之间的秒数差。
     * @param currentTime 当前时间，单位为毫秒。
     * @param totalTime 总时间，单位为毫秒。
     * @return 当前时间与总时间之间的秒数差。
     */
    fun calculateSecond(currentTime: Long, totalTime: Long): Int {

        val millisDiff: Long = totalTime - currentTime

        return (millisDiff / 1000.0).toInt()
    }

    /**
     * 获取当前进度
     */
    fun getProgress(currentPosition: Long, duration: Long): Int {
        return (currentPosition * 100 * 1.0f / duration + 0.5f).toInt()
    }

    /**
     *
     * 获取缓冲进度
     */
    fun getSecondaryProgress(bufferedPos: Long, duration: Long): Int {
        return (bufferedPos * 100 * 1.0f / duration + 0.5f).toInt()
    }

}