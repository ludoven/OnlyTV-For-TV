package com.quiet.onlytv.ui.search

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.R
import com.quiet.onlytv.databinding.FragmentSearchBinding
import com.quiet.onlytv.ui.adapter.FavoriteAdapter
import com.quiet.onlytv.utils.requestFocused

class SearchFragment : BaseFragment<MainActivity,FragmentSearchBinding,DefaultPresenter>() {

    private lateinit var searchAdapter: FavoriteAdapter

    override fun initView() {
        searchAdapter = FavoriteAdapter(mutableListOf("","","","","","","",""))
        binding.searchRv.adapter = searchAdapter
    }

    override fun initData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP && binding.edit.hasFocus()){
            getAttachActivity()?.requestFocusTab()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        super.enableFocusableAndRequestFirstFocused()
        return binding.edit.requestFocus()
    }
}