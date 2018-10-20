package com.lambadam.domain.auth

import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.interactor.UseCase.None
import com.lambadam.domain.model.User

class GetCurrentUser(private val manager: AuthManager,
                     dispatcherProvider: CoroutineDispatcherProvider)
    : UseCase<User, None>(dispatcherProvider) {

    override suspend fun buildUseCase(params: None) = manager.getCurrentUser()
}