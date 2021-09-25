package com.lgjy.deeper.common.app

import android.app.Application
import com.lgjy.deeper.common.BuildConfig

/**
 * Created by LGJY on 2021/9/22.
 * Email：yujye@sina.com
 *
 * Global application instance
 */

object DeepApplication {

    /**
     * Application can be obtained anywhere
     */
    lateinit var instance: Application
        internal set

    /**
     * App version code
     */
    val versionCode: Int = BuildConfig.VERSION_CODE

    /**
     * App version name
     */
    val versionName: String = BuildConfig.VERSION_NAME
}
