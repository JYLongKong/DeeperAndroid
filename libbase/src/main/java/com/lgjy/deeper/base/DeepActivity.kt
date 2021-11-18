package com.lgjy.deeper.base

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity

/**
 * Created by LGJY on 2021/11/7.
 * Email：yujye@sina.com
 *
 * Tip19: Single Activity with multiple Fragments
 *
 * Main activity containing multi-fragment
 */

class DeepActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep)
    }
}
