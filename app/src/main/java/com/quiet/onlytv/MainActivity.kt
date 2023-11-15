package com.quiet.onlytv

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ludoven.base.BaseActivity
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.base.Constant
import com.quiet.onlytv.databinding.ActivityMainBinding
import com.quiet.onlytv.ui.adapter.HomeTabAdapter
import com.quiet.onlytv.ui.category.CategoryFragment
import com.quiet.onlytv.ui.favorite.FavoriteFragment
import com.quiet.onlytv.ui.home.HomeFragment
import com.quiet.onlytv.ui.movie.MovieFragment
import com.quiet.onlytv.ui.search.SearchFragment
import com.quiet.onlytv.ui.set.SetFragment
import com.quiet.onlytv.ui.show.ShowFragment
import com.quiet.onlytv.utils.AnimationUtil
import com.quiet.onlytv.utils.AnimationUtil.animateWithCallback
import com.quiet.onlytv.utils.MyAnimationListener
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.requestFocused
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, DefaultPresenter>(), OnItemSelectedListener {

    private lateinit var homeTabAdapter: HomeTabAdapter
    private lateinit var currentFragment: BaseFragment<*, *, *>

    private var fragmentList: MutableList<BaseFragment<*, *, *>> = ArrayList()
    private val titleList = mutableListOf("Home", "Categories", "Movies", "Shows", "Favorites")

    private var mLastFocusView: View? = null
    private var exitTime: Long = 0

    override fun initView() {
        initTab()
        initViewPager()
        binding.searchTab.setOnFocusChangeListener { view, b ->
            selectFragment(
                fragmentList.lastIndex,
                b,
                view
            )
        }
        binding.icon.setOnFocusChangeListener { view, b ->
            selectFragment(
                0,
                b,
                view
            )
        }

    }


    override fun initData() {

    }


    private fun initTab() {
        homeTabAdapter = HomeTabAdapter(titleList).apply {
            setSelectedListener(this@MainActivity)
        }
        binding.homeTab.apply {
            adapter = homeTabAdapter
            post {
                if (childCount > 0) getChildAt(0).requestFocused()
            }
        }
    }

    private fun initViewPager() {
        fragmentList.clear()
        fragmentList.add(SetFragment())
        fragmentList.add(HomeFragment())
        fragmentList.add(CategoryFragment())
        fragmentList.add(MovieFragment())
        fragmentList.add(ShowFragment())
        fragmentList.add(FavoriteFragment())
        fragmentList.add(SearchFragment())
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


    override fun onItemSelected(
        adapter: BaseQuickAdapter<*, *>?,
        v: View?,
        pos: Int,
        hasFocus: Boolean
    ) {
        selectFragment(pos + 1, hasFocus, v)
    }

    private fun selectFragment(pos: Int, hasFocus: Boolean, v: View?) {
        if (hasFocus) {
            binding.viewPager.setCurrentItem(pos, false)
            v?.isSelected = true
            if (v != mLastFocusView) {
                mLastFocusView?.isSelected = false
            }

        } else {
            mLastFocusView = v
        }
    }


    fun requestFocusTab() {
        if (!binding.topLayout.isVisible) {
            startShowAnimation()
            return
        }
        Timber.d("$mLastFocusView")
        mLastFocusView?.requestFocused()
    }

    fun hideTopBar(hide: Boolean, animation: Boolean = false) {
        Log.d(TAG, "hideTopBar: hide:${hide};animation:${animation}")
        if (hide) {
            if (animation) {
                startHideAnimation()
            } else {
                binding.topLayout.isVisible = false
            }
        } else {
            if (animation) {
                startShowAnimation()
            } else {
                binding.topLayout.isVisible = true
            }
        }
    }

    private fun startHideAnimation() {
        if (!binding.topLayout.isVisible) {
            return
        }
        binding.topLayout.animateWithCallback(this, Constant.Anim.TOP_EXIT, 300, {

        }, {
            binding.topLayout.visibility = View.GONE
        })
    }

    private fun startShowAnimation() {
        Timber.d("startShowAnimation: isVisible:${binding.topLayout.isVisible}")
        if (binding.topLayout.isVisible) {
            return
        }
        binding.topLayout.animateWithCallback(this, Constant.Anim.TOP_ENTER, 300, {
            binding.topLayout.visibility = View.VISIBLE
        }, {

        })
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                in Constant.KeyCode.selectKeys, KeyEvent.KEYCODE_DPAD_DOWN->{
                    if (binding.icon.hasFocus() || binding.homeTab.hasFocus() || binding.searchTab.hasFocus()) {
                        currentFragment.enableFocusableAndRequestFirstFocused()
                        return true
                    }
                }

               in Constant.KeyCode.backKeys-> {
                    if (binding.icon.hasFocus() || binding.homeTab.hasFocus() || binding.searchTab.hasFocus()) {
                        if (System.currentTimeMillis() - exitTime > 2000) {
                            showToast(R.string.exit)
                            exitTime = System.currentTimeMillis()
                        } else {
                            App.exitApp()
                        }
                        return true
                    }
                }
            }

            if (currentFragment.dispatchKeyEvent(event)) {
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

}