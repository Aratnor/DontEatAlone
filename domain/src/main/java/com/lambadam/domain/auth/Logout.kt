package com.lambadam.domain.auth

import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result

class Logout(private val manager: AuthManager,
             dispatcherProvider: CoroutineDispatcherProvider)
    : UseCase<None, Nothing>(dispatcherProvider) {

    override suspend fun buildUseCase(params: Nothing?): Result<Exception, None> = manager.logout()
}