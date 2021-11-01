package com.lgjy.deeper.common.util

import android.app.Application
import android.widget.Toast
import javax.inject.Inject

/**
 * Created by LGJY on 2021/9/22.
 * Email：yujye@sina.com
 *
 * Show toast
 */

object ShowToast {

    fun show(msg: String, tag: String? = null) {
        LogP.i(tag ?: "===Toast", msg)
        Toast.makeText(application, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLong(msg: String, tag: String? = null) {
        LogP.i(tag ?: "===ToastLong", msg)
        Toast.makeText(application, msg, Toast.LENGTH_LONG).show()
    }
}
