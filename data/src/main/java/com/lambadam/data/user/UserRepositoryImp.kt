package com.lambadam.data.user

import com.google.firebase.firestore.FirebaseFirestore
import com.lambadam.data.user.UserConstants.COLLECTION_USER
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User
import com.lambadam.domain.repository.UserRepository
import kotlinx.coroutines.experimental.tasks.await

class UserRepositoryImp(private val firestore: FirebaseFirestore): UserRepository {

    override suspend fun getUser(userId: String): Result<Exception, User> {
        val user = try {
            firestore.collection(COLLECTION_USER)
                    .document(userId)
                    .get()
                    .await()
                    .toObject(User::class.java)
        }catch (e: Exception){
            return Result.buildError(e)
        }

        return user?.let {
            Result.buildValue { it }
        } ?: Result.buildError(IllegalArgumentException("There is no such user"))
    }

    companion object {
        @Volatile private var INSTANCE: UserRepositoryImp? = null

        fun getInstance(firestore: FirebaseFirestore): UserRepositoryImp {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: UserRepositoryImp(firestore).also { INSTANCE = it }
            }
        }
    }
}