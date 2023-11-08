package com.ludoven.base.http

import android.util.Log
import io.reactivex.Observer

abstract class BaseObserver<T> : Observer<T> {


    /**
     * 打印日志到控制台
     * throwable.printStackTrace();
     * 如果你某个地方不想使用全局错误处理,
     * 则重写 onError(Throwable) 并将 super.onError(e); 删掉
     * 如果你不仅想使用全局错误处理,还想加入自己的逻辑,
     * 则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
     */
    override fun onError(throwable: Throwable) {
        val apiException = requestHandle(throwable)
        Log.e("TAG", "requestHandle:  ${apiException.errorCode}  ${apiException.message} ")
    }

    override fun onComplete() {}

    protected abstract fun onError(errorCode: Int, errorMsg: String?)

    /**
     * 统一处理Throwable
     * @param e e
     * @return msg
     */
    protected fun requestHandle(e: Throwable): ApiException {
        val ex: ApiException
        Log.e("TAG", "requestHandle: " + e.message)
        when (e) {
            is ApiException -> {
                ex = ApiException(e,e.errorCode)
                //后台异常，在这里你可以toast弹窗或者进行其他处理，具体需根据业务结合
                Log.e("TAG", "requestHandle: $ex")
                onError(ex.errorCode,e.message)
            }
            else -> {
                ex = ApiException(e, ERROR.UNKNOWN)
                ex.message = "Network Error"
                onError(ERROR.UNKNOWN,ex.message)
            }
        }

        return ex
    }



    /**
     * 约定异常
     */
    internal object ERROR {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        const val NETWORK_ERROR = 1002

        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003
    }
}