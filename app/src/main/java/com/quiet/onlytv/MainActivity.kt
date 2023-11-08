package com.quiet.onlytv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ludoven.base.BaseActivity
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.databinding.ActivityMainBinding
import com.quiet.onlytv.ui.adapter.HomeTabAdapter

class MainActivity : BaseActivity<ActivityMainBinding,DefaultPresenter>() {

    private lateinit var homeTabAdapter: HomeTabAdapter
    private val titleList = mutableListOf("Home","Categories","Movies","Shows","Favorites")
    override fun initView() {

    }

    override fun initData() {
        homeTabAdapter = HomeTabAdapter(titleList)
        binding.homeTab.adapter = homeTabAdapter
    }

    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }
}