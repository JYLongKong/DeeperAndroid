package com.lgjy.deeper.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lgjy.deeper.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by LGJY on 2021/11/26.
 * Email：yujye@sina.com
 *
 * Login ViewModel
 */

@HiltViewModel
internal class LoginVM @Inject constructor() : BaseViewModel() {

    private val _username = MutableLiveData<String>("")
    val username: LiveData<String> = _username

    fun asd() {
        vMIOScope
    }
}
