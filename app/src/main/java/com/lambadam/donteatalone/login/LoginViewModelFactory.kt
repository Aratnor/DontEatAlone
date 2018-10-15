package com.lambadam.donteatalone.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambadam.domain.auth.Login
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.donteatalone.Injection

class LoginViewModelFactory(private val login: Login,
                            private val dispatcherProvider: CoroutineDispatcherProvider)
    : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(LoginViewModel::class.java))
            throw IllegalArgumentException("Model class must be TasksViewModel")

        @Suppress("UNCHECKED_CAST")
        return LoginViewModel(login,dispatcherProvider) as T
    }

    companion object {

        private var INSTANCE: LoginViewModelFactory? = null

        fun getInstance(context: Context): LoginViewModelFactory{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: LoginViewModelFactory(
                        Injection.provideLogin(context),
                        Injection.dispatcherProvider)
                        .also { INSTANCE = it }
            }
        }
    }
}