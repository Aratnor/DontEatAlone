package com.lambadam.domain.auth

import com.lambadam.domain.exception.Error
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User


interface AuthManager {

    suspend fun login(type: AuthType, token: String): Result<Error, Unit>

    fun getCurrentUser(): Result<Error, User>

    fun logout(): Result<Error, Unit>
}