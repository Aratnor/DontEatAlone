package com.lambadam.donteatalone.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.auth.Login
import com.lambadam.domain.auth.Login.*
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.model.None
import com.lambadam.donteatalone.base.BaseViewModel
import kotlinx.coroutines.experimental.launch

class LoginViewModel(private val loginUseCase: Login,
        dispatcher: CoroutineDispatcherProvider): BaseViewModel(dispatcher) {

    private val _login = MutableLiveData<None>()

    val login: LiveData<None>
    get() = _login

    fun login(type: AuthType, token: String) = launch {

        loginUseCase.execute(Params(type,token)) { it.result({handleFailure()},::loginSuccess)  }
    }

    private fun loginSuccess(none: None){
        _login.value = none
    }
}