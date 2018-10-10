package com.lambadam.domain.auth

import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User

class GetCurrentUser(private val manager: AuthManager): UseCase<User, Nothing>() {

    override fun buildUseCase(params: Nothing?): Result<Exception, User> = manager.getCurrentUser()
}