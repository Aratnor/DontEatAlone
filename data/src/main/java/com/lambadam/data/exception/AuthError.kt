package com.lambadam.data.exception

import com.lambadam.domain.exception.Error.AuthError

sealed class AuthError : AuthError() {
    object NoSignedInUser: AuthError()
    object DuplicatedEmail : AuthError()
    object DisabledAccount : AuthError()
}