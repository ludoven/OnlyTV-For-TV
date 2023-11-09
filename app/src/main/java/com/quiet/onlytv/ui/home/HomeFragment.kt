package com.quiet.onlytv.ui.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.R
import com.quiet.onlytv.databinding.FragmentHomeBinding
import com.quiet.onlytv.ui.adapter.BannerAdapter
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener
import com.quiet.onlytv.widget.rv.TopSmoothScroller
import java.security.Key


class HomeFragment : BaseFragment<MainActivity, FragmentHomeBinding, DefaultPresenter>(),
    OnItemSelectedListener, OnKeyListener {

    private lateinit var bannerAdapter: BannerAdapter

    override fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        bannerAdapter = BannerAdapter(mutableListOf("", "", "", ""), this,this)
        /*      val snapHelper = LinearSnapHelper()
              snapHelper.attachToRecyclerView(binding.bannerRv)*/
        binding.bannerRv.adapter = bannerAdapter
    }

    override fun initData() {

    }

    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun onItemSelected(
        adapter: BaseQuickAdapter<*, *>?,
        v: View?,
        pos: Int,
        hasFocus: Boolean
    ) {
     /*   if (hasFocus) {
            v?.let { binding.bannerRv.scrollToCenterView(it) }
        }*/
    }

    override fun onKeyDown(pos: Int, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN){
            if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
                val nextPos = pos + 1
                if (nextPos in 0 until bannerAdapter.data.size){
                    smoothScrollToPos(nextPos)
                }
                return true
            }
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
                val nextPos = pos -1
                if (nextPos in 0 until bannerAdapter.data.size){
                    smoothScrollToPos(nextPos)
                }
                return true
            }
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP){
                getAttachActivity()?.requestFocusTab()
                return true
            }
        }
        return false
    }

    private fun smoothScrollToPos(pos:Int){
        val scroller= TopSmoothScroller(requireContext())
        scroller.targetPosition=pos
        binding.bannerRv.layoutManager!!.startSmoothScroll(scroller)
    }

}