package com.quiet.onlytv.ui.set

import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
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


class SetFragment : BaseFragment<MainActivity, FragmentSetBinding, DefaultPresenter>(),
    View.OnFocusChangeListener {

    private var fragmentList: MutableList<BaseFragment<*, *, *>> = ArrayList()
    private lateinit var currentFragment: BaseFragment<*, *, *>

    override fun initView() {
        binding.setAbout.onFocusChangeListener = this
        binding.setSub.onFocusChangeListener = this
        binding.setLan.onFocusChangeListener = this
        binding.setHistory.onFocusChangeListener = this
        binding.setHelp.onFocusChangeListener = this
        initViewPager()
    }

    override fun initData() {

    }

    private fun initViewPager() {
        fragmentList.clear()
        fragmentList.add(AboutFragment())
        fragmentList.add(SubtitleFragment())
        fragmentList.add(HistoryFragment())
        fragmentList.add(HelpFragment())
        val viewPagerAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragmentList.size

            override fun createFragment(position: Int): Fragment = fragmentList[position]
        }
        binding.viewPager.run {
            registerOnPageChangeCallback(onPageChangeCallback)
            adapter = viewPagerAdapter
            isUserInputEnabled = false
            offscreenPageLimit = fragmentList.size
        }
        disablePagerInnerRvFocus()
    }


    private val onPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentFragment = fragmentList[position]
            currentFragment.focusSearchEnable(true)

            for (i in fragmentList.indices) {
                fragmentList[i].focusSearchEnable(i == position)
            }
        }
    }

    /**
     * 禁止ViewPager2内部类的RecycleView获得焦点
     */
    private fun disablePagerInnerRvFocus() {
        for (i in 0 until binding.viewPager.childCount) {
            if (binding.viewPager.getChildAt(i) is RecyclerView) {
                binding.viewPager.getChildAt(i).isFocusable = false
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP && binding.setAbout.hasFocus()) {
            getAttachActivity()?.requestFocusTab()
            return true
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && binding.root.hasFocus()) {
            return currentFragment.enableFocusableAndRequestFirstFocused()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        super.enableFocusableAndRequestFirstFocused()
        return binding.setAbout.requestFocus()
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p1) {
            val pos = when (p0) {
                binding.setAbout -> {
                    0
                }

                binding.setSub -> {
                    1
                }

                binding.setHistory -> {
                    2
                }

                binding.setHelp -> {
                    3
                }

                else -> {
                    0
                }
            }
            binding.viewPager.setCurrentItem(pos, false)
        }
    }

    fun requestTabFocused(pos: Int) {
        when (pos) {
            0 -> {
                binding.setAbout.requestFocused()
            }

            1 -> {
                binding.setSub.requestFocused()
            }

            2 -> {
                binding.setHistory.requestFocused()
            }

            3 -> {
                binding.setHelp.requestFocused()
            }
        }
    }
}