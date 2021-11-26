package com.lgjy.deeper.base.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {

    // Capability of launching coroutine within IO scope
    val vMIOScope = CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO)
}
