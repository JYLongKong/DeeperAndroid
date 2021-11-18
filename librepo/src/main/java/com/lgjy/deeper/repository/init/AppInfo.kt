package com.lgjy.deeper.repository.init

import com.lgjy.deeper.repository.BuildConfig

/**
 * Created by LGJY on 2021/10/22.
 * Email：yujye@sina.com
 *
 * Application information
 */

object AppInfo {

    /**
     * App version code
     */
    var versionCode: Int = -1
        private set

    /**
     * App version name
     */
    var versionName: String = ""
        private set

    fun init() {
        versionCode = BuildConfig.VERSION_CODE
        versionName = BuildConfig.VERSION_NAME
    }
}
