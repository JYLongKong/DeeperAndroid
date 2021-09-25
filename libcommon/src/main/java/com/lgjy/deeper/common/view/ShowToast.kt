package com.lgjy.deeper.common.view

import android.widget.Toast
import com.lgjy.deeper.common.app.DeepApplication
import com.lgjy.deeper.common.log.LogP

/**
 * Created by LGJY on 2021/9/22.
 * Email：yujye@sina.com
 *
 * Just show toast
 */

object ShowToast {

    fun show(msg: String, tag: String? = null) {
        LogP.i(tag ?: "===Toast", msg)
        Toast.makeText(DeepApplication.instance, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLong(msg: String, tag: String? = null) {
        LogP.i(tag ?: "===ToastLong", msg)
        Toast.makeText(DeepApplication.instance, msg, Toast.LENGTH_LONG).show()
    }
}
