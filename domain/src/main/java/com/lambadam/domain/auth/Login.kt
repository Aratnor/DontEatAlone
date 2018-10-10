package com.lambadam.domain.auth

import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.auth.Login.Params
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result

class Login(private val manager: AuthManager): UseCase<None, Params>() {

    override fun buildUseCase(params: Params?): Result<Exception, None> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return manager.login(params.type, params.token)
    }

    data class Params(val type: AuthType, val token: String)
}