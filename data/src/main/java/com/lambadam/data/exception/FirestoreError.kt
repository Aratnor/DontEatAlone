package com.lambadam.data.exception

import com.lambadam.domain.exception.Error.NetworkError

sealed class FirestoreError : NetworkError() {

    object Unavailable: FirestoreError()
    object NotFound: FirestoreError()
    object PermissionDenied : FirestoreError()
}