package com.lgjy.deeper.network.core

import android.net.ParseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * Created by LGJY on 2021/11/1.
 * Email：yujye@sina.com
 *
 * Tip15: Encapsulates the call result of the api with sealed class
 *
 * ApiResult
 *  ->Success
 *  ->Failure
 *    ->Error       Business error
 *    ->Exception   Throwable
 */

internal sealed class ApiResult<out T> {

    data class Success<T>(val response: Response<ApiResponse<T>>) : ApiResult<T>() {
        val body: ApiResponse<T>? = response.body()
    }

    sealed class Failure {

        data class Error<T>(val response: Response<ApiResponse<T>>) : ApiResult<T>() {
            val body: ApiResponse<T>? = response.body()
        }

        data class Exception<T>(val e: Throwable) : ApiResult<T>()
    }
}

/**
 * ApiResp to ApiResult
 */
internal suspend fun <T> ApiResp<T>.toApiResult(): ApiResult<T> {
    return kotlin.runCatching {
        this.await()
    }.fold(
        onFailure = { ApiResult.Failure.Exception(it) },
        onSuccess = { resp ->
            if (resp.body()?.code == API_SUCCESS) {
                ApiResult.Success(resp)
            } else {
                ApiResult.Failure.Error(resp)
            }
        }
    )
}

/**
 * Execute api and work with its result
 */
internal fun <T> ApiResult<T>.execute(onError: (Int, String) -> Unit, onSuccess: (T) -> Unit) {
    when (this) {
        is ApiResult.Success -> {
            body?.data?.let(onSuccess) ?: onError(CODE_NULL_DATA, MSG_NULL_DATA)
        }
        is ApiResult.Failure.Error -> {
            // Tip17: Destructuring declaration
            val (code, msg) = catchRespError(this)
            onError(code, msg)
        }
        is ApiResult.Failure.Exception -> {
            val (code, msg) = catchRespException(this)
            onError(code, msg)
        }
    }
}

/**
 * Catch business error of api and unified processing
 */
private fun <T> catchRespError(apiResult: ApiResult.Failure.Error<T>): Pair<Int, String> {
    return with(apiResult) {
        if (response.code() != HttpURLConnection.HTTP_OK) {
            response.code() to response.message()
        } else {
            val errorCode = body?.code ?: CODE_UNKNOWN_ERROR
            val errorMsg = when (errorCode) {
                ERROR_LOGIN_EXPIRED -> MSG_LOGIN_EXPIRED
                else -> MSG_UNKNOWN_ERROR
            }
            errorCode to errorMsg
        }
    }
}

/**
 * Catch exception of api and unified processing
 */
private fun <T> catchRespException(apiResult: ApiResult.Failure.Exception<T>): Pair<Int, String> {
    return when (val e = apiResult.e) {
        is HttpException -> e.code() to e.message()
        is SSLException -> CODE_SSL_ERROR to MSG_SSL_ERROR
        is UnknownHostException, is ConnectException, is SocketTimeoutException -> CODE_CONNECT_FAIL to MSG_CONNECT_FAIL
        is ParseException, is JSONException, is JsonDataException, is JsonEncodingException ->
            CODE_PARSE_ERROR to MSG_PARSE_ERROR
        else -> CODE_UNKNOWN_ERROR to MSG_UNKNOWN_ERROR
    }
}
