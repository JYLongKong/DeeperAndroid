package com.lgjy.deeper.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lgjy.deeper.base.mvvm.BaseViewModel
import com.lgjy.deeper.repository.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by LGJY on 2021/11/26.
 * Email：yujye@sina.com
 *
 * Login And Register ViewModel
 */

@HiltViewModel
internal class LoginVM @Inject constructor(private val userRepo: UserRepo) : BaseViewModel() {

    private val _username = MutableLiveData<String>("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    private val _rePassword = MutableLiveData<String>("")
    val rePassword: LiveData<String> = _rePassword

    fun login() {

    }

    fun register() {

    }
}
