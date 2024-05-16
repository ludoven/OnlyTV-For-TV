package com.quiet.onlytv.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.quiet.onlytv.base.Constant
import com.quiet.onlytv.databinding.LayoutPlayerViewBinding
import com.quiet.onlytv.utils.AnimationUtil.animateWithCallback
import com.quiet.onlytv.utils.PlayerUtils
import com.quiet.onlytv.utils.focusSearchEnable
import timber.log.Timber

@UnstableApi
/**
 *
 *  author： ludoven
 *  date :   2023/11/15 13:46
 *
 */
class PlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), Player.Listener, AnalyticsListener {

    private val animTime = 300L
    private lateinit var binding: LayoutPlayerViewBinding
    private var mPlayer: ExoPlayer? = null

    /** 是否正在拖动 */
    private var isDragging = false

    init {
        initView()
    }

    private fun initView() {
        binding = LayoutPlayerViewBinding.inflate(LayoutInflater.from(context), this, true)
        initPlayer()
    }

    private fun initPlayer() {
        mPlayer = ExoPlayer.Builder(context).build().apply {
            setVideoSurfaceView(binding.surfaceView)
            addListener(this@PlayerView)
            addAnalyticsListener(this@PlayerView)
        }
        initSeek()
    }


    override fun onPositionDiscontinuity(
        eventTime: AnalyticsListener.EventTime,
        oldPosition: Player.PositionInfo,
        newPosition: Player.PositionInfo,
        reason: Int
    ) {
        val formatPlayTime = PlayerUtils.formatPlayTime(newPosition.positionMs)
        val duration = PlayerUtils.formatPlayTime(mPlayer?.duration ?: 0)
        Timber.d("currentPos:   $formatPlayTime  :  $duration")
        binding.playerTime.text = formatPlayTime
        binding.playerTotalTime.text = duration
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        removeCallbacks(hideControllerRunnable)
        removeCallbacks(showControllerRunnable)

        //如果播控界面可见 则7s后自动隐藏
        if (binding.controllerLayout.isVisible) {
            // 隐藏控制面板
            postDelayed(hideControllerRunnable, 7000)
        }

        when (keyCode) {
            in Constant.KeyCode.selectKeys -> {
            }

            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT -> {
                if (!binding.controllerLayout.isVisible) {
                    showController(true)
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showController() {
        if (binding.controllerLayout.isVisible) {
            return
        }
        binding.controllerLayout.isVisible = true
        binding.controllerLayout.animateWithCallback(
            context,
            Constant.Anim.BOTTOM_ENTER,
            animTime, {
                binding.controllerLayout.focusSearchEnable(true)
            }, {})
    }

    private fun hideController() {
        if (!binding.controllerLayout.isVisible) {
            return
        }
        binding.controllerLayout.animateWithCallback(context, Constant.Anim.BOTTOM_EXIT, animTime, {
            binding.controllerLayout.focusSearchEnable(false)
        }, {
            binding.controllerLayout.isInvisible = true
        })
    }

    private fun showController(show: Boolean) {
        removeCallbacks(hideControllerRunnable)
        removeCallbacks(showControllerRunnable)
        if (show) {
            post(showControllerRunnable)
            postDelayed(hideControllerRunnable, 7000)
        } else {
            post(hideControllerRunnable)
        }
    }

    private fun initSeek() {
        binding.playerSeekbar.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (binding.controllerLayout.animation != null) {
                return@OnKeyListener false
            }
            when (event.action) {
                KeyEvent.ACTION_DOWN -> {
                    removeCallbacks(showControllerRunnable)
                    removeCallbacks(hideControllerRunnable)
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        isDragging = true
                    }
                }

                KeyEvent.ACTION_UP -> {
                    isDragging = false
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (mPlayer != null) {
                            val progress: Int = binding.playerSeekbar.progress
                            val seekPos = (progress * 1.0f / 100 * mPlayer!!.duration).toLong()
                            mPlayer?.seekTo(seekPos)
                        }
                        postDelayed(hideControllerRunnable, 7000)
                    }
                }
            }
            false
        })
    }

    fun startPlay(url: String) {
        if (url.isBlank()) {
            return
        }
        val mediaItem = MediaItem.fromUri(url)
        mPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    fun onPause() {
        if (mPlayer != null) {
            mPlayer?.pause()
        }
    }

    fun onStop() {
        if (mPlayer != null) {
            mPlayer?.stop()
        }
    }

    fun releasePlayer() {
        if (mPlayer != null) {
            mPlayer?.release()
            mPlayer = null
        }
    }

    private val showControllerRunnable = Runnable {
        showController()
    }

    private val hideControllerRunnable = Runnable {
        hideController()
    }

}