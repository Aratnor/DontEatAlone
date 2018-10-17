package com.lambadam.data.user

import com.google.firebase.firestore.FirebaseFirestore
import com.lambadam.data.entity.UserEntity
import com.lambadam.data.mapper.EntityMapper
import com.lambadam.data.user.UserConstants.COLLECTION_USER
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User
import com.lambadam.domain.repository.UserRepository
import kotlinx.coroutines.experimental.tasks.await

class UserRepositoryImp(private val firestore: FirebaseFirestore,
                        private val mapper: EntityMapper<User, UserEntity>): UserRepository {

    override suspend fun getUser(userId: String): Result<Exception, User> {
        val userEntity = try {
            firestore.collection(COLLECTION_USER)
                    .document(userId)
                    .get()
                    .await()
                    .toObject(UserEntity::class.java)
        }catch (e: Exception){
            return Result.buildError(e)
        }

        return userEntity?.let {
            Result.buildValue { mapper.mapFromEntity(it) }
        } ?: Result.buildError(IllegalArgumentException("There is no such user"))
    }

    companion object {
        @Volatile private var INSTANCE: UserRepositoryImp? = null

        fun getInstance(firestore: FirebaseFirestore,
                        mapper: EntityMapper<User, UserEntity>): UserRepositoryImp {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: UserRepositoryImp(firestore, mapper).also { INSTANCE = it }
            }
        }
    }
}