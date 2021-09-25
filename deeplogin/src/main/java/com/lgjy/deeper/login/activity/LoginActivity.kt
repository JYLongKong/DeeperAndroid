package com.lgjy.deeper.login.activity

import android.os.Bundle
import com.lgjy.deeper.base.mvvm.BaseActivity
import com.lgjy.deeper.login.R

/**
 * Created by LGJY on 2021/9/25.
 * Email：yujye@sina.com
 */

internal class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
