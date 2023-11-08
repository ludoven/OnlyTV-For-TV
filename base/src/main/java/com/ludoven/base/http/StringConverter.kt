package com.ludoven.base.http

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

/**
 * Created by le on 2020/5/8.
 */
class StringConverter : Converter<ResponseBody, String> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): String {
        return value.string()
    }

    companion object {
        @JvmField
        val INSTANCE = StringConverter()
    }
}