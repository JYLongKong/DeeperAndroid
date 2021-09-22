package com.youyes.deeper.common.json

import com.squareup.moshi.Moshi

/**
 * Created by LGJY on 2021/9/22.
 * Email：yujye@sina.com
 *
 * Json Convertor
 */

object JsonConvertor {

    val moshi: Moshi by lazy { Moshi.Builder().build() }

    inline fun <reified T> fromString(string: String): T? {
        return moshi.adapter(T::class.java).fromJson(string)
    }

    inline fun <reified T> toString(t: T): String {
        return moshi.adapter(T::class.java).toJson(t)
    }
}
