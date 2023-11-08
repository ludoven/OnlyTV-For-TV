package com.ludoven.base.mvp

import com.ludoven.base.http.ApiException
import com.ludoven.base.http.BaseObserver
import com.ludoven.base.http.RetryWithDelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * Created by ludoven on 2018/8/9.
 */
open class BasePresenter<V: BaseContract.IView> : BaseContract.IPresenter {
    protected var mView: V? = null
    private var weakView: WeakReference<BaseContract.IView>? = null

    private var mCompositeDisposable: CompositeDisposable? = null


    override fun attachView(view: BaseContract.IView) {
        weakView = WeakReference(view)
        mView = weakView!!.get() as V?
    }

    override fun detachView() {
        if (mView != null) {
            mView = null
            weakView!!.clear()
            weakView = null
        }
    }

    override fun cancelAll() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    open fun removeDisposable(disposable: Disposable?) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.remove(disposable!!)
        }
    }


    /**
     *  kotlin  封装扩展
     */

    fun <T> Observable<T>.ss(position :Int=0,onSuccess: (T) -> Unit){
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen(RetryWithDelay())
            .subscribe(object : BaseObserver<T>() {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onNext(t: T & Any) {
                    onSuccess.invoke(t)
                }


                override fun onError(errorCode: Int, errorMsg: String?) {
                    mView?.showError(position,errorCode, errorMsg)
                }
            })
    }


    fun <T> Observable<T>.sss(position :Int=0,onSuccess: (T) -> Unit, onError: ((ApiException) -> Unit)){
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen(RetryWithDelay())
            .subscribe(object : BaseObserver<T>() {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onNext(t: T & Any) {
                    onSuccess.invoke(t)
                }


                override fun onError(errorCode: Int, errorMsg: String?) {
                    mView?.showError(position,errorCode, errorMsg)
                }
            })
    }
}