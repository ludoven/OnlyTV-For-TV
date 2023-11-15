package com.quiet.onlytv.ui.category

import android.view.KeyEvent
import android.view.View
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.databinding.FragmentCategoryBinding
import com.quiet.onlytv.ui.adapter.CategoryAdapter
import com.quiet.onlytv.utils.OnKeyListener
import com.quiet.onlytv.utils.requestFocused
import com.quiet.onlytv.widget.rv.BaseRecyclerView

class CategoryFragment : BaseFragment<MainActivity, FragmentCategoryBinding, DefaultPresenter>(),
    OnKeyListener {

    private lateinit var categoryAdapter: CategoryAdapter

    private val spanCount = 4

    override fun initView() {
        val mutableListOf = mutableListOf<String>(
            "Action",
            "Adventure",
            "Comedy",
            "Drama",
            "Animation",
            "Science Fiction",
            "Romance",
            "Horror",
            "Documentary",
            "War",
            "Ethics",
            "Music"
        )
        categoryAdapter = CategoryAdapter(mutableListOf).apply {
            setKeyListener(this@CategoryFragment)
        }
        binding.categoryRv.adapter = categoryAdapter
        binding.categoryRv.canFocusOutHorizontal(false)
    }

    override fun initData() {

    }


    override fun createPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun onAdapterKeyEvent(pos: Int, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP && isFirstRow(pos,spanCount)) {
            getAttachActivity()?.requestFocusTab()
            return true
        }
        return false
    }

    private fun isFirstRow(position: Int, spanCount: Int): Boolean {
        return position < spanCount
    }


    override fun enableFocusableAndRequestFirstFocused(): Boolean {
        super.enableFocusableAndRequestFirstFocused()
        return if (binding.categoryRv.childCount > 0) {
            binding.categoryRv.getChildAt(0)?.requestFocused()
            true
        } else {
            false
        }
    }
}