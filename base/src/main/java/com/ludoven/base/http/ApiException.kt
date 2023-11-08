package com.ludoven.base.http

class ApiException(throwable: Throwable?, code: Int) :
    Exception(throwable) {
    override var message: String? = throwable?.message
    var errorCode: Int = code
}

class ServerException : java.lang.RuntimeException() {
    var code = 0
    override var message: String? = null
}