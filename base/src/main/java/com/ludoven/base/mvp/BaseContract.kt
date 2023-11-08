package com.ludoven.base.mvp

import com.ludoven.base.BaseBean

/**
 * @author ludoven
 */
interface BaseContract {
    interface IPresenter {
        fun attachView(view: IView)
        fun detachView()
        fun cancelAll()
    }

    interface IView {
        /**
         * @param flag 用于标记对应接口
         */
        fun showError(flag: Int = 0, errorCode: Int, errorMsg: String?)
        fun showResult(flag: Int = 0, bean: BaseBean<String>)
    }


}