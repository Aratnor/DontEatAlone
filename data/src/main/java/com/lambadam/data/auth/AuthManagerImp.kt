package com.lambadam.data.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import com.lambadam.domain.auth.AuthManager
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.auth.AuthType.FACEBOOK
import com.lambadam.domain.auth.AuthType.GOOGLE
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User
import com.lambadam.domain.model.wrapIntoResult
import kotlinx.coroutines.experimental.tasks.await

class AuthManagerImp : AuthManager {

    private val auth = FirebaseAuth.getInstance()

    override fun getCurrentUser(): Result<Exception, User> {
        return wrapIntoResult {
            auth.currentUser?.let {
                User(it.uid,
                        it.uid,
                        it.displayName.orEmpty(),
                        it.displayName.orEmpty(),
                        it.email.orEmpty(),
                        it.photoUrl.toString())
            } ?: throw FirebaseNoSignedInUserException("There is no signed in user")
        }
    }

    override fun logout(): Result<Exception, None> {
        return wrapIntoResult {
            auth.signOut()
            None()
        }
    }

    override suspend fun login(type: AuthType, token: String): Result<Exception, None> {
        val credential = when(type){
            FACEBOOK -> FacebookAuthProvider.getCredential(token)
            GOOGLE -> GoogleAuthProvider.getCredential(token, null)
        }
        return loginWithCredential(credential)
    }

    private suspend fun loginWithCredential(credential: AuthCredential): Result<Exception, None> {
        return wrapIntoResult {
            auth.signInWithCredential(credential).await()
            None()
        }
    }
}