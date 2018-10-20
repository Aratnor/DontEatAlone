package com.lambadam.donteatalone.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.auth.Login
import com.lambadam.domain.auth.Login.Params
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.donteatalone.base.BaseViewModel
import kotlinx.coroutines.experimental.launch

class LoginViewModel(private val login: Login,
                     dispatcher: CoroutineDispatcherProvider): BaseViewModel(dispatcher) {

    private val _loginSuccess = MutableLiveData<Unit>()

    val loginSuccess: LiveData<Unit>
    get() = _loginSuccess

    fun login(type: AuthType, token: String) = launch {

        login.execute(Params(type,token)) { result({handleFailure()}, ::loginSuccess)  }
    }

    private fun loginSuccess(u: Unit){
        _loginSuccess.value = u
    }
}