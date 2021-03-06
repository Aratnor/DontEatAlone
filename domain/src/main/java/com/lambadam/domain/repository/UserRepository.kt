package com.lambadam.domain.repository

import com.lambadam.domain.exception.Error
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User

interface UserRepository {

    suspend fun getUser(userId: String): Result<Error, User>
}