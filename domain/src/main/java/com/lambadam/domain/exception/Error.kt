package com.lambadam.domain.exception

sealed class Error: Throwable() {

    abstract class AuthError: Error()
    abstract class NetworkError: Error()
    object Unknown: Error()
}