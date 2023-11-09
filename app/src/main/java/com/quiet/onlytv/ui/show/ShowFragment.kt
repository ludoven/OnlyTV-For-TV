package com.quiet.onlytv.ui.show

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ludoven.base.BaseFragment
import com.ludoven.base.mvp.DefaultPresenter
import com.quiet.onlytv.MainActivity
import com.quiet.onlytv.R
import com.quiet.onlytv.databinding.FragmentShowBinding


class ShowFragment : BaseFragment<MainActivity, FragmentShowBinding, DefaultPresenter>() {
    override fun getPresenter(): DefaultPresenter {
        return DefaultPresenter()
    }

    override fun initView() {

    }

    override fun initData() {

    }

}