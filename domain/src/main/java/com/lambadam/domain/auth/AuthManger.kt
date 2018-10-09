package com.lambadam.domain.auth

import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User


interface AuthManger {

    fun login(type: AuthType, token: String): Result<Exception, None>

    fun getCurrentUser(): Result<Exception, User>

    fun logout(): Result<Exception, None>
}