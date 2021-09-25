package com.lgjy.deeper.flutter

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity

internal class FlutterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter)
    }
}
