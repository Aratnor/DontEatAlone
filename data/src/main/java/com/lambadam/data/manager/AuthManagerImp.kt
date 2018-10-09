package com.lambadam.data.manager

import com.lambadam.domain.auth.AuthManager
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User

class AuthManagerImp : AuthManager {

    override fun getCurrentUser(): Result<Exception, User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout(): Result<Exception, None> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(type: AuthType, token: String): Result<Exception, None> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}