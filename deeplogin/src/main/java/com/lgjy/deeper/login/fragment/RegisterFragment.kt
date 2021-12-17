package com.lgjy.deeper.login.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.lgjy.deeper.base.mvvm.BaseFragment
import com.lgjy.deeper.login.R
import com.lgjy.deeper.login.databinding.FragmentRegisterBinding
import com.lgjy.deeper.login.viewmodel.LoginVM

/**
 * Created by LGJY on 2021/11/18.
 * Email：yujye@sina.com
 *
 * User registration
 */

internal class RegisterFragment : BaseFragment() {

    private val loginVM: LoginVM by navGraphViewModels(R.id.graph_login)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentRegisterBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@RegisterFragment
            vm = loginVM
        }.root
    }
}
