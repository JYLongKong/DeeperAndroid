package com.lgjy.deeper.graphics.activity

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity
import com.lgjy.deeper.graphics.R

/**
 * Created by LGJY on 2021/9/23.
 * Email：yujye@sina.com
 *
 * Vulkan demo
 */

class VulkanActivity : BaseActivity() {

    init {
        System.loadLibrary("bn-vulkan-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphics)
    }
}
