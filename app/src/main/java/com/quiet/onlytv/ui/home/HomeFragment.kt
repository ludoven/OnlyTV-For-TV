package com.quiet.onlytv.ui.home

import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.databinding.FragmentHomeBinding
import com.quiet.onlytv.ui.adapter.BannerAdapter
import com.quiet.onlytv.ui.adapter.HomeListAdapter
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener
import com.quiet.onlytv.widget.rv.BaseRecyclerView
import com.quiet.onlytv.widget.rv.TopSmoothScroller


class HomeFragment : BaseFragment<MainActivity, FragmentHomeBinding, DefaultPresenter>(),
    OnItemSelectedListener, OnKeyListener {

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var homeListAdapter :HomeListAdapter

    override fun initView() {
        initAdapter()
        initListAdapter()
    }

    private fun initAdapter() {
        bannerAdapter = BannerAdapter(mutableListOf("", "", "", "", ""), this,this)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView( binding.bannerRv)
        binding.bannerRv.adapter = bannerAdapter
        binding.indicator.attachToRecyclerView(binding.bannerRv,pagerSnapHelper)

        binding.bannerRv.setOnFocusListener(object :BaseRecyclerView.OnListener{
            override fun onFocusGain(child: View?, focued: View?) {
                super.onFocusGain(child, focued)
                getAttachActivity()?.hideTopBar(hide = false, animation = true)
            }
        })

    }

    override fun initData() {

    }

    private fun initListAdapter(){
        homeListAdapter = HomeListAdapter(mutableListOf("","",""),object :OnItemSelectedListener{
            override fun onItemSelected(
                adapter: BaseQuickAdapter<*, *>?,
                v: View?,
                pos: Int,
                hasFocus: Boolean
            ) {

            }

        })
        binding.listRv.adapter = homeListAdapter

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
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP){
                binding.root.smoothScrollTo(0,0)
                getAttachActivity()?.requestFocusTab()
                return true
            }
            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
                getAttachActivity()?.hideTopBar(hide = true, animation = true)
            }
        }
        return false
    }

}