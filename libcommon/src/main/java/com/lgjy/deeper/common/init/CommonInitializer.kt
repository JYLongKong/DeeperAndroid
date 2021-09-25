package com.lgjy.deeper.common.init

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.lgjy.deeper.common.app.DeepApplication

/**
 * Created by LGJY on 2021/9/22.
 * Email：yujye@sina.com
 *
 * Common module initializer based on App Startup
 *
 * 如果需要手动初始化，需要先在AndroidManifest文件meta-data节点中添加tools:node="remove"
 * 再手动调用AppInitializer.getInstance(this).initializeComponent(XXXInitializer::class.java)
 */

internal class CommonInitializer : Initializer<DeepApplication> {

    /**
     * contains all of the necessary operations to initialize the component and returns an instance of T
     * call ordering: Application.attachBaseContext()->ContentProvider.onCreate()->Application.onCreate()
     */
    override fun create(context: Context): DeepApplication {
        DeepApplication.instance = context.applicationContext as Application
        return DeepApplication
    }

    /**
     * returns a list of the other Initializer<T> objects that the initializer depends on
     */
    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}
