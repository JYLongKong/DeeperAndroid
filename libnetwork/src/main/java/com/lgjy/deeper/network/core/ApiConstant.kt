package com.lgjy.deeper.network.core

/**
 * Created by LGJY on 2021/11/2.
 * Email：yujye@sina.com
 *
 * Constant value about api
 */

internal const val API_SUCCESS: Int = 0

internal const val CODE_UNKNOWN_ERROR: Int = -1
internal const val MSG_UNKNOWN_ERROR: String = "unknown error"

// api exception
internal const val CODE_NULL_DATA: Int = 1
internal const val MSG_NULL_DATA: String = "null data"
internal const val CODE_CONNECT_FAIL: Int = 2
internal const val MSG_CONNECT_FAIL: String = "connect fail"
internal const val CODE_PARSE_ERROR: Int = 3
internal const val MSG_PARSE_ERROR: String = "parse error"
internal const val CODE_SSL_ERROR: Int = 4
internal const val MSG_SSL_ERROR: String = "ssl error"

// business error
internal const val ERROR_LOGIN_EXPIRED: Int = 101
internal const val MSG_LOGIN_EXPIRED: String = "login expired"
