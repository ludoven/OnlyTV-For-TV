package com.quiet.onlytv.ui.set

import android.graphics.Color
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
import com.quiet.onlytv.databinding.FragmentSubtitleBinding
import com.quiet.onlytv.utils.requestFocused


class SubtitleFragment : BaseFragment<MainActivity,FragmentSubtitleBinding,DefaultPresenter>() {

    override fun initView() {
        binding.switchBt.setColor(Color.parseColor("#A8C8FF"),Color.parseColor("#00468A"))
    }

    override fun initData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_DPAD_LEFT,KeyEvent.KEYCODE_BACK->{
                val setFragment = parentFragment as SetFragment
                setFragment.requestTabFocused(1)
                return true
            }
            KeyEvent.KEYCODE_DPAD_CENTER->{
                if (binding.switchLayout.hasFocus()){
                    binding.switchBt.setChecked(!binding.switchBt.isChecked())
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        binding.switchLayout.requestFocused()
        return true
    }
}