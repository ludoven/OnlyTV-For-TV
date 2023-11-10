package com.quiet.onlytv.ui.set

import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.databinding.FragmentSetBinding
import com.quiet.onlytv.ui.category.CategoryFragment
import com.quiet.onlytv.ui.favorite.FavoriteFragment
import com.quiet.onlytv.ui.home.HomeFragment
import com.quiet.onlytv.ui.movie.MovieFragment
import com.quiet.onlytv.ui.search.SearchFragment
import com.quiet.onlytv.ui.show.ShowFragment
import com.quiet.onlytv.utils.requestFocused


class SetFragment : BaseFragment<MainActivity, FragmentSetBinding, DefaultPresenter>() {


    override fun initView() {

    }

    override fun initData() {

    }



    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP && binding.setAbout.hasFocus()){
            getAttachActivity()?.requestFocusTab()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        super.enableFocusableAndRequestFirstFocused()
        return binding.setAbout.requestFocus()
    }
}