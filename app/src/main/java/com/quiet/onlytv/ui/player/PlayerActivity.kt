package com.quiet.onlytv.ui.player

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.util.UnstableApi
import com.ludoven.base.BaseActivity
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.R
import com.quiet.onlytv.databinding.ActivityPlayerBinding

@UnstableApi class PlayerActivity : BaseActivity<ActivityPlayerBinding,DefaultPresenter>() {

    companion object{
        fun start(context: Context){
            val intent = Intent(context,PlayerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        binding.playerView.startPlay("https://vod-progressive.akamaized.net/exp=1700122367~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2345%2F16%2F411729198%2F1768601290.mp4~hmac=b037098370ca51dbe31bbb1f8acbe3625a781b85806b2f6a2e3d42894a21a9ec/vimeo-prod-skyfire-std-us/01/2345/16/411729198/1768601290.mp4?download=1&filename=production_id%3A4237848+%28720p%29.mp4")
    }

    override fun initData() {}

    override fun onPause() {
        super.onPause()
        binding.playerView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.playerView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.playerView.releasePlayer()
    }

}