package com.quiet.onlytv.ui.home

import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.base.Constant
import com.quiet.onlytv.databinding.FragmentHomeBinding
import com.quiet.onlytv.ui.adapter.BannerAdapter
import com.quiet.onlytv.ui.adapter.HomeListAdapter
import com.quiet.onlytv.ui.info.InfoActivity
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener
import com.quiet.onlytv.utils.requestFocused
import com.quiet.onlytv.widget.rv.BaseRecyclerView
import com.quiet.onlytv.widget.rv.TopSmoothScroller
import timber.log.Timber


class HomeFragment : BaseFragment<MainActivity, FragmentHomeBinding, DefaultPresenter>(),
    OnItemSelectedListener, OnKeyListener, BaseRecyclerView.OnListener {

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var homeListAdapter :HomeListAdapter

    override fun initView() {
        initAdapter()
        initListAdapter()
    }

    private fun initAdapter() {
        bannerAdapter = BannerAdapter(mutableListOf("", "", "", "", "")).apply {
            setKeyListener(this@HomeFragment)
            setSelectedListener(this@HomeFragment)
        }
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView( binding.bannerRv)
        binding.bannerRv.adapter = bannerAdapter
        binding.indicator.attachToRecyclerView(binding.bannerRv,pagerSnapHelper)

        binding.bannerRv.setOnFocusListener(this)
        bannerAdapter.setOnItemClickListener{_,_,_->
            context?.let { InfoActivity.start(it) }
        }
    }

    override fun initData() {}

    private fun initListAdapter(){
        homeListAdapter = HomeListAdapter(mutableListOf("","",""))
        binding.listRv.adapter = homeListAdapter
        homeListAdapter.setOnItemClickListener{_,_,pos ->
            context?.let { InfoActivity.start(it) }
        }
    }

    override fun createPresenter(): DefaultPresenter {
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

    override fun onAdapterKeyEvent(pos: Int, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN){
            when(keyCode){
                KeyEvent.KEYCODE_DPAD_UP->{
                    binding.root.smoothScrollTo(0,0)
                    getAttachActivity()?.requestFocusTab()
                    return true
                }
                KeyEvent.KEYCODE_DPAD_DOWN->{
                    getAttachActivity()?.hideTopBar(hide = true, animation = true)
                }
            }
        }
        return false
    }

    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        super.enableFocusableAndRequestFirstFocused()
        return if (binding.bannerRv.childCount > 0) {
            binding.bannerRv.getChildAt(0)?.requestFocused()
            true
        } else {
            false
        }
    }

    override fun onFocusGain(child: View?, focued: View?) {
        super.onFocusGain(child, focued)
        binding.root.post {
            binding.root.smoothScrollTo(0,0)
        }
        getAttachActivity()?.hideTopBar(hide = false, animation = true)
    }

}