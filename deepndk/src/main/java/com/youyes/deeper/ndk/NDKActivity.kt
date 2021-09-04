package com.youyes.deeper.ndk

import android.os.Bundle
import com.youyes.deeper.base.mvvm.BaseActivity

internal class NDKActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ndk)
    }
}
