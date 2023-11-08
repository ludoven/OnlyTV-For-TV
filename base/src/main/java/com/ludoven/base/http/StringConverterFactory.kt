package com.ludoven.base.http

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *
 * @author ludoven
 * @date 2020/5/8
 */
class StringConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (type === String::class.java) {
            StringConverter.INSTANCE
        } else null
        //其它类型我们不处理，返回null就行
    }

    companion object {
        private val INSTANCE = StringConverterFactory()
        fun create(): StringConverterFactory {
            return INSTANCE
        }
    }
}