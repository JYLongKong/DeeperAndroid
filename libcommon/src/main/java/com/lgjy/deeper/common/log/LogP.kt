package com.lgjy.deeper.common.log

import android.util.Log

/**
 * Created by LGJY on 19/8/2.
 * Email：yujye@sina.com
 *
 * Log Proxy
 */

object LogP {

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    fun e(tag: String, msg: String, throwable: Throwable? = null) {
        Log.e(tag, msg, throwable)
    }
}
