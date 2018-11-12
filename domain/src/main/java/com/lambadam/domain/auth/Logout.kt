package com.lambadam.domain.auth

import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.interactor.UseCase.None

class Logout(private val manager: AuthManager,
             dispatcherProvider: CoroutineDispatcherProvider)
    : UseCase<Unit, None>(dispatcherProvider) {

    override suspend fun buildUseCase(params: None) = manager.logout()
}