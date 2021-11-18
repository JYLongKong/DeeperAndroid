package com.lgjy.deeper.network.core

import kotlinx.coroutines.Deferred
import retrofit2.Response

/**
 * Created by LGJY on 2021/11/1.
 * Email：yujye@sina.com
 *
 * Unified api response format
 */

internal data class ApiResponse<T>(
    val code: Int,
    val message: String = "",
    val data: T?
)

/**
 * Tip14: "typealias" simplify data structure
 */
internal typealias ApiResp<T> = Deferred<Response<ApiResponse<T>>>
