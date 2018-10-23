package com.lambadam.data.auth

import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.lambadam.data.common.helper.AuthHelper
import com.lambadam.data.common.helper.FirestoreHelper
import com.lambadam.data.entity.UserEntity
import com.lambadam.data.exception.AuthError
import com.lambadam.domain.auth.AuthManager
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.auth.AuthType.FACEBOOK
import com.lambadam.domain.auth.AuthType.GOOGLE
import com.lambadam.domain.exception.Error
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User
import com.lambadam.domain.model.wrapIntoResult
import kotlinx.coroutines.experimental.withContext

class AuthManagerImp(private val auth: FirebaseAuth,
                     private val db: FirebaseFirestore,
                     private val authHelper: AuthHelper,
                     private val firestoreHelper: FirestoreHelper,
                     private val dispatcher: CoroutineDispatcherProvider) : AuthManager {

    override fun getCurrentUser(): Result<Error, User> {
        return auth.currentUser?.let {
            Result.buildValue {
                User(it.uid,
                        it.uid,
                        it.displayName.orEmpty(),
                        it.email.orEmpty(),
                        it.photoUrl.toString()
                )
            }
        } ?: Result.buildError(AuthError.NoSignedInUser)
    }

    override fun logout(): Result<Error, Unit> {
        return wrapIntoResult { auth.signOut() }
    }

    override suspend fun login(type: AuthType, token: String): Result<Error, Unit> {
        val credential = when(type){
            FACEBOOK -> FacebookAuthProvider.getCredential(token)
            GOOGLE -> GoogleAuthProvider.getCredential(token, null)
        }
        return loginWithCredential(credential)
    }

    private suspend fun loginWithCredential(credential: AuthCredential): Result<Error, Unit> {
        val task = auth.signInWithCredential(credential)
        val authResult = try {
            withContext(dispatcher.io){ authHelper.result(task) }
        }catch (e: Error){
            return Result.buildError(e)
        }
        return checkIsNewUser(authResult)
    }

    private suspend fun checkIsNewUser(result: AuthResult?): Result<Error, Unit> {
        return result?.let{
            if (!it.additionalUserInfo.isNewUser){
                Result.buildValue { }
            }else {
                saveUser(it)
            }
        } ?: Result.buildError(Error.Unknown)
    }

    private suspend fun saveUser(result: AuthResult): Result<Error, Unit> {

        val userEntity = result.user.run {
            UserEntity(uid, uid, displayName.orEmpty(), email.orEmpty(), photoUrl.toString())
        }

        val task = db.collection("users").document(userEntity.id).set(userEntity)
        return try {
            withContext(dispatcher.io){ firestoreHelper.tryTask(task)}
            Result.buildValue {  }
        } catch (e: Error){
            Result.buildError(e)
        }
    }

    companion object {
        @Volatile private var INSTANCE: AuthManagerImp? = null

        fun getInstance(auth: FirebaseAuth,
                        db: FirebaseFirestore,
                        authHelper: AuthHelper,
                        firesstoreHelper: FirestoreHelper,
                        dispatcher: CoroutineDispatcherProvider): AuthManagerImp{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: AuthManagerImp(auth,
                        db,
                        authHelper,
                        firesstoreHelper,
                        dispatcher).also { INSTANCE = it }
            }
        }
    }
}