package com.youyes.deeper.graphic.activity

import android.app.NativeActivity

/**
 * Created by LGJY on 2021/9/23.
 * Email：yujye@sina.com
 *
 * Vulkan demo
 */

class VulkanActivity : NativeActivity() {

    init {
        System.loadLibrary("bn-vulkan-lib")
    }
}
