package com.lambadam.domain.auth

import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.auth.Login.Params
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result

class Login(private val manager: AuthManager,
            dispatcherProvider: CoroutineDispatcherProvider)
    : UseCase<None, Params>(dispatcherProvider) {

    override suspend fun buildUseCase(params: Params) = manager.login(params.type, params.token)

    data class Params(val type: AuthType, val token: String)
}