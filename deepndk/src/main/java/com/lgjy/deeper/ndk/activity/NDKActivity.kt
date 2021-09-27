package com.lgjy.deeper.ndk.activity

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity
import com.lgjy.deeper.ndk.R

internal class NDKActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ndk)
    }
}
