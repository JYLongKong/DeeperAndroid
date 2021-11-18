package com.lgjy.deeper.base.ext

import android.view.View
import androidx.navigation.findNavController

/**
 * Created by LGJY on 2021/11/7.
 * Email：yujye@sina.com
 *
 * Navigation extension functions
 */

object NavigationExt {

    /**
     * NavigateUp when click back icon
     */
    fun back(view: View) {
        view.findNavController().navigateUp()
    }
}
