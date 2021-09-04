package com.youyes.deeper.base

import android.os.Bundle
import com.youyes.deeper.base.mvvm.BaseActivity

class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
