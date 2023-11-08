package com.ludoven.base.http

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author ludoven
 * @date 2021/10/25
 * @desc 请求重连
 */
class RetryWithDelay : Function<Observable<out Throwable>, Observable<*>> {

    private var maxRetryCount = 3 // 可重试次数
    private var retryDelayMillis: Long = 3000 // 重试等待时间

    //重试次数
    private var retryCount = 1
    constructor() {
        this.retryCount =1
    }

    constructor(retryDelayMillis: Long) {
        this.retryDelayMillis = retryDelayMillis
    }

    constructor(maxRetryCount: Int, retryDelayMillis: Long) {
        this.maxRetryCount = maxRetryCount
        this.retryDelayMillis = retryDelayMillis
    }


    @Throws(java.lang.Exception::class)
    override fun apply(observable: Observable<out Throwable?>): Observable<*>? {
        return observable.flatMap(object : Function<Throwable?, ObservableSource<*>?> {


            override fun apply(t: Throwable): ObservableSource<*>? {
                if (t is IOException){
                    if (++retryCount <= maxRetryCount) {
//                    LogUtil.d("RetryWhenHandler--retryCount:$retryCount")
                        //   if (!TextUtils.isEmpty(tag)) LogUtil.d("RetryWhenHandler--retryCount:$retryCount,tag:$tag")
                        return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
                    }
                }
                return Observable.error<Any>(t)
            }
        })
    }

    private inner class Wrapper(val index: Int, val throwable: Throwable)

}
