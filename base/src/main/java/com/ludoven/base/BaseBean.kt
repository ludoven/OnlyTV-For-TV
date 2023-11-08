package com.ludoven.base



data class BaseBean<T>(
    var data: T,
    var status: Int,
    var message:String
)

