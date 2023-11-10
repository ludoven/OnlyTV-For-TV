package com.quiet.onlytv.ui.movie

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.R
import com.quiet.onlytv.databinding.FragmentMovieBinding
import com.quiet.onlytv.ui.adapter.HomeListAdapter
import com.quiet.onlytv.ui.adapter.RecommendAdapter
import com.quiet.onlytv.utils.OnItemSelectedListener
import com.quiet.onlytv.utils.OnKeyListener
import com.quiet.onlytv.utils.requestFocused
import com.quiet.onlytv.widget.rv.BaseRecyclerView

class MovieFragment : BaseFragment<MainActivity,FragmentMovieBinding,DefaultPresenter>(),
    OnKeyListener {

    private lateinit var recommendAdapter: RecommendAdapter
    private lateinit var homeListAdapter : HomeListAdapter

    override fun initView() {
        initRecommendAdapter()
        initListAdapter()
    }

    override fun initData() {

    }

    private fun initRecommendAdapter(){
        val mutableListOf = mutableListOf<String>(
            "Super Puppy",
            "Power Sisters",
            "Super Puppy",
            "Power Sisters",
            "Super Puppy",
            "Power Sisters",
            "Super Puppy"
        )
        recommendAdapter = RecommendAdapter(mutableListOf).apply {
            setKeyListener(this@MovieFragment)
        }
        binding.recommendRv.adapter = recommendAdapter
        binding.recommendRv.setOnFocusListener(object : BaseRecyclerView.OnListener{
            override fun onFocusGain(child: View?, focued: View?) {
                super.onFocusGain(child, focued)
                binding.root.post {
                    binding.root.smoothScrollTo(0,0)
                }
                getAttachActivity()?.hideTopBar(hide = false, animation = true)
            }
        })
    }
    private fun initListAdapter(){
        homeListAdapter = HomeListAdapter(mutableListOf("","",""))
        binding.movieRv.adapter = homeListAdapter
    }


    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun onKeyDown(pos: Int, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            binding.root.smoothScrollTo(0,0)
            getAttachActivity()?.requestFocusTab()
            return true
        }
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            getAttachActivity()?.hideTopBar(hide = true, animation = true)
        }
        return false
    }

    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        super.enableFocusableAndRequestFirstFocused()
        return if (binding.recommendRv.childCount > 0) {
            binding.recommendRv.getChildAt(0)?.requestFocused()
            true
        } else {
            false
        }
    }
}