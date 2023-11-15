package com.quiet.onlytv.ui.info

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ludoven.base.BaseActivity
import com.ludoven.base.mvp.DefaultPresenter
import com.ludoven.base.utils.singleClick
import com.quiet.onlytv.R
import com.quiet.onlytv.databinding.ActivityInfoBinding
import com.quiet.onlytv.ui.adapter.ActorAdapter
import com.quiet.onlytv.ui.adapter.HomeHorizontalAdapter
import com.quiet.onlytv.ui.player.PlayerActivity
import com.quiet.onlytv.utils.requestFocused

class InfoActivity : BaseActivity<ActivityInfoBinding,DefaultPresenter>() {

    companion object{
        fun start(context: Context){
            val intent = Intent(context,InfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        binding.titleTv.text = "Vikram Rana"
        binding.desTv.text = "A fearless warrior embarks on a thrilling journey to protect his kingdom, discovering his true destiny along the way."
        binding.infoTv.text = "PG-13  • 2022 (US)  • Action, Superhero • 1h 54m"
        binding.directorContent.text = "Alexandre Dimitrov"
        binding.writerContent.text = "Georgi Horvath"
        binding.screenplayContent.text = "Ben Jackson"
        binding.watchBt.requestFocused()
        Glide.with(this).load(R.drawable.icon_recommend).into(binding.infoBg)

        binding.watchBt.singleClick {
            PlayerActivity.start(this)
        }
        initActorAdapter()
        initHorizontalAdapter()
    }

    private fun initActorAdapter() {
        val adapter = ActorAdapter(mutableListOf("","","","","","","","",""))
        binding.actorRv.adapter = adapter
        binding.actorRv.canFocusOutHorizontal(false)
    }

    private fun initHorizontalAdapter() {
        val adapter = HomeHorizontalAdapter(mutableListOf("","","","","","","","",""))
        binding.relatedRv.adapter = adapter
        binding.relatedRv.canFocusOutHorizontal(false)
    }

    override fun initData() {

    }


}