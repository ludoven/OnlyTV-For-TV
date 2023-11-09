package com.quiet.onlytv.ui.category

import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<MainActivity,FragmentCategoryBinding,DefaultPresenter>() {
    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun initView() {

    }

    override fun initData() {

    }

}