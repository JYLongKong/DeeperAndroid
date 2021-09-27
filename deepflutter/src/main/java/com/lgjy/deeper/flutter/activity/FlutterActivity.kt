package com.lgjy.deeper.flutter.activity

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity
import com.lgjy.deeper.flutter.R

internal class FlutterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter)
    }
}
