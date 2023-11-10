package com.quiet.onlytv.ui.favorite

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.R
import com.quiet.onlytv.databinding.FragmentFavoriteBinding
import com.quiet.onlytv.ui.adapter.FavoriteAdapter
import com.quiet.onlytv.ui.adapter.FavoriteTabAdapter
import com.quiet.onlytv.utils.OnKeyListener

class FavoriteFragment : BaseFragment<MainActivity, FragmentFavoriteBinding, DefaultPresenter>(),
    OnKeyListener {

    private lateinit var tabAdapter: FavoriteTabAdapter
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun initView() {
        initTabAdapter()
        initListAdapter()
    }


    override fun initData() {

    }

    private fun initTabAdapter() {
        tabAdapter = FavoriteTabAdapter(
            mutableListOf(
                "Movies",
                "TV Shows",
                "Added last week",
                "Available in 4K"
            )
        )
        binding.tabRv.adapter = tabAdapter
        binding.tabRv.canFocusOutHorizontal(false)
        tabAdapter.setKeyListener(this@FavoriteFragment)
        tabAdapter.setOnItemClickListener { adapter, view, position ->
            tabAdapter.setSelectIndex(position)
        }
    }

    private fun initListAdapter() {
        val strings = mutableListOf<String>()
        for (i in 0..15) {
            strings.add("")
        }
        favoriteAdapter = FavoriteAdapter(strings)
        binding.favoriteRv.adapter = favoriteAdapter
    }

    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun onKeyDown(pos: Int, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            getAttachActivity()?.requestFocusTab()
            return true
        }
        return false
    }
}