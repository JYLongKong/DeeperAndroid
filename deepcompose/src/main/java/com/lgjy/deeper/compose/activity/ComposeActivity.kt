package com.lgjy.deeper.compose.activity

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity
import com.lgjy.deeper.compose.R

internal class ComposeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
    }
}
