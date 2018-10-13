package com.lambadam.domain.auth

import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User

class GetCurrentUser(private val manager: AuthManager,
                     dispatcherProvider: CoroutineDispatcherProvider)
    : UseCase<User, Nothing>(dispatcherProvider) {

    override suspend fun buildUseCase(params: Nothing?): Result<Exception, User> = manager.getCurrentUser()
}