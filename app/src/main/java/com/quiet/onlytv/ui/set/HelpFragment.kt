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
import com.quiet.onlytv.databinding.FragmentHelpBinding
import com.quiet.onlytv.utils.requestFocused


class HelpFragment : BaseFragment<MainActivity,FragmentHelpBinding,DefaultPresenter>() {
    override fun initView() {

    }

    override fun initData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_DPAD_LEFT,KeyEvent.KEYCODE_BACK->{
                val setFragment = parentFragment as SetFragment
                setFragment.requestTabFocused(3)
                return true
            }
            KeyEvent.KEYCODE_DPAD_CENTER->{
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        binding.helpFaq.requestFocused()
        return true
    }
}