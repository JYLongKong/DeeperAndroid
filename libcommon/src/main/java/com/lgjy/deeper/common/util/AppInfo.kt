package com.lgjy.deeper.common.util

import kotlin.properties.Delegates

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
    var versionCode by Delegates.notNull<Int>()

    /**
     * App version name
     */
    lateinit var versionName: String
}
