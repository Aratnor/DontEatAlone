package com.lambadam.data.common.helper

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.lambadam.data.exception.AuthError
import com.lambadam.domain.exception.Error
import kotlinx.coroutines.experimental.tasks.await

class AuthHelper {

    @Throws(Error::class)
    suspend fun <T> result(task: Task<T>): T {
        return try {
            task.await()
        } catch (e: FirebaseAuthUserCollisionException){
            throw AuthError.DuplicatedEmail
        } catch (e: FirebaseAuthInvalidUserException){
            throw AuthError.DisabledAccount
        }
    }
}