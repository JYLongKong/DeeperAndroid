package com.lgjy.deeper.common.manager

import com.lgjy.deeper.common.BuildConfig

/**
 * Created by LGJY on 2021/10/22.
 * Email：yujye@sina.com
 *
 * Application information manager
 */

class AppManager {

    /**
     * App version code
     */
    val versionCode: Int = BuildConfig.VERSION_CODE

    /**
     * App version name
     */
    val versionName: String = BuildConfig.VERSION_NAME
}