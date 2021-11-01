package com.lgjy.deeper.common.util

import com.squareup.moshi.Moshi

/**
 * Created by LGJY on 2021/9/22.
 * Email：yujye@sina.com
 *
 * Tip4: Json Convertor
 */

object JsonConvertor {

    val moshi: Moshi by lazy { Moshi.Builder().build() }

    inline fun <reified T> fromJson(json: String): T? {
        return moshi.adapter(T::class.java).fromJson(json)
    }

    inline fun <reified T> toJson(t: T): String {
        return moshi.adapter(T::class.java).toJson(t)
    }
}
