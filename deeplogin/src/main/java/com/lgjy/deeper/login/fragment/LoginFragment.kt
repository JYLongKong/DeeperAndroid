package com.lgjy.deeper.login.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.lgjy.deeper.base.mvvm.BaseFragment
import com.lgjy.deeper.login.R
import com.lgjy.deeper.login.databinding.FragmentLoginBinding
import com.lgjy.deeper.login.viewmodel.LoginVM

/**
 * Created by LGJY on 2021/9/25.
 * Email：yujye@sina.com
 *
 * Login by typing username and password
 */

class LoginFragment : BaseFragment() {

    // Tip21: Create ViewModel by "by navGraphViewModels"
    private val loginVM: LoginVM by navGraphViewModels(R.id.graph_login)

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
    }
}
