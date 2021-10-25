package com.lgjy.deeper.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by LGJY on 2021/10/22.
 * Email：yujye@sina.com
 *
 * Main Application with Hilt
 * Hilt must contain an Application class that is annotated with @HiltAndroidApp
 */

// Tip12: @HiltAndroidApp triggers Hilt's code generation
@HiltAndroidApp
class DeepApplication : Application()
