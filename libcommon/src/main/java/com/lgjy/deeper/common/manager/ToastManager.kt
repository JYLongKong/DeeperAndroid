package com.lgjy.deeper.common.manager

import android.content.Context
import android.widget.Toast
import com.lgjy.deeper.common.log.LogP

/**
 * Created by LGJY on 2021/9/22.
 * Email：yujye@sina.com
 *
 * Show toast
 */

class ToastManager(private val context: Context) {

    fun show(msg: String, tag: String? = null) {
        LogP.i(tag ?: "===Toast", msg)
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLong(msg: String, tag: String? = null) {
        LogP.i(tag ?: "===ToastLong", msg)
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}
