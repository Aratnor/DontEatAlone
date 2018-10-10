package com.lambadam.domain.auth

import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result

class Logout(private val manager: AuthManager): UseCase<None, Nothing>() {

    override fun buildUseCase(params: Nothing?): Result<Exception, None> = manager.logout()
}