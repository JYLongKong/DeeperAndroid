package com.lgjy.deeper.login.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lgjy.deeper.base.mvvm.BaseFragment
import com.lgjy.deeper.login.databinding.FragmentLoginBinding
import com.lgjy.deeper.login.viewmodel.LoginVM

/**
 * Created by LGJY on 2021/9/25.
 * Email：yujye@sina.com
 *
 * User login by typing username and password
 */

class LoginFragment : BaseFragment() {

    private val loginVM: LoginVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@LoginFragment
            vm = loginVM
            event = Event()
        }.root
    }

    inner class Event {

        fun goToRegister() {

        }

        fun login() {

        }
    }
}
