package com.lambadam.data.auth

import android.util.Log
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
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
import java.util.*

class AuthManagerImp(private val auth: FirebaseAuth,private val db: FirebaseFirestore) : AuthManager {

    override fun getCurrentUser(): Result<Exception, User> {
        return auth.currentUser?.let {
            Result.buildValue {
                User(it.uid,
                        it.uid,
                        it.displayName.orEmpty(),
                        it.email.orEmpty(),
                        it.photoUrl.toString()
                )
            }
        } ?: Result.buildError(FirebaseNoSignedInUserException("There is no signed in user"))
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
        return try {
            val result = auth.signInWithCredential(credential).await()
            checkIsNewUser(result)
        } catch (e: Exception) {
            Result.buildError(e)
        }
    }

    private suspend fun checkIsNewUser(result: AuthResult?): Result<Exception, None> {
        return result?.let{
            if (!it.additionalUserInfo.isNewUser){
            Result.buildValue { None() }
            }else {
                saveUser(it)
            }
        } ?: Result.buildError(IllegalArgumentException("AuthResult is null"))
    }

    private suspend fun saveUser(result: AuthResult): Result<Exception, None> {

        val firebaseUser = result.user
        val user = User(firebaseUser.uid,
                firebaseUser.uid,
                firebaseUser.displayName.orEmpty(),
                firebaseUser.email.orEmpty(),
                firebaseUser.photoUrl.toString())
        return wrapIntoResult {
            db.collection("users").add(user).await()
            None()
        }
    }

    companion object {
        @Volatile private var INSTANCE: AuthManagerImp? = null

        fun getInstance(auth: FirebaseAuth,db: FirebaseFirestore): AuthManagerImp{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: AuthManagerImp(auth,db)
                        .also { INSTANCE = it }
            }
        }
    }
}