package com.lambadam.domain.auth

import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User


interface AuthManager {

    suspend fun login(type: AuthType, token: String): Result<Exception, Unit>

    fun getCurrentUser(): Result<Exception, User>

    fun logout(): Result<Exception, Unit>
}