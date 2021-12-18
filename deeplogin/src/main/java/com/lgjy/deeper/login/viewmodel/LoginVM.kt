package com.lgjy.deeper.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lgjy.deeper.base.mvvm.BaseViewModel
import com.lgjy.deeper.login.repo.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by LGJY on 2021/11/26.
 * Email：yujye@sina.com
 *
 * Login And Register ViewModel
 */

@HiltViewModel
internal class LoginVM @Inject constructor(private val loginRepo: LoginRepo) : BaseViewModel() {

    val username = MutableLiveData<String>("")

    val password = MutableLiveData<String>("")

    val rePassword = MutableLiveData<String>("")

    fun login() {

    }

    fun register() {

    }
}
