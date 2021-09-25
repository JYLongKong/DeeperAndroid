package com.lgjy.deeper.ndk

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity

internal class NDKActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ndk)
    }
}
