package com.quiet.onlytv.ui.set

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
import com.quiet.onlytv.databinding.FragmentHistoryBinding
import com.quiet.onlytv.ui.adapter.HistoryAdapter
import com.quiet.onlytv.utils.requestFocused


class HistoryFragment : BaseFragment<MainActivity,FragmentHistoryBinding,DefaultPresenter>() {

    private lateinit var historyAdapter: HistoryAdapter

    override fun initView() {
        historyAdapter = HistoryAdapter(mutableListOf("Earth","封神","Infinite","万物生灵","流浪地球","庆余年"))
        binding.historyRv.adapter = historyAdapter
    }

    override fun initData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_BACK->{
                val setFragment = parentFragment as SetFragment
                setFragment.requestTabFocused(2)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        binding.clearBt.requestFocused()
        return true
    }

}