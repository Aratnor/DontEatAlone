package com.lambadam.donteatalone

import com.lambadam.data.auth.AuthManagerImp
import com.lambadam.domain.auth.Login
import com.lambadam.donteatalone.executor.CoroutineDispatcherProviderImp

object Injection {
    private val authManager = AuthManagerImp()
    val dispatcherProvider = CoroutineDispatcherProviderImp()

    fun provideLogin() = Login(authManager,dispatcherProvider)
}
