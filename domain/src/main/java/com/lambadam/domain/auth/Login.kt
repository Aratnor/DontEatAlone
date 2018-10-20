package com.lambadam.domain.auth

import com.lambadam.domain.auth.Login.Params
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.interactor.UseCase

class Login(private val manager: AuthManager,
            dispatcherProvider: CoroutineDispatcherProvider)
    : UseCase<Unit, Params>(dispatcherProvider) {

    override suspend fun buildUseCase(params: Params) = manager.login(params.type, params.token)

    data class Params(val type: AuthType, val token: String)
}