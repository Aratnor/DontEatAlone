package com.lambadam.data.user

import com.google.firebase.firestore.FirebaseFirestore
import com.lambadam.data.common.helper.FirestoreHelper
import com.lambadam.data.entity.UserEntity
import com.lambadam.data.exception.FirestoreError
import com.lambadam.data.mapper.EntityMapper
import com.lambadam.data.user.UserConstants.COLLECTION_USER
import com.lambadam.domain.exception.Error
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User
import com.lambadam.domain.repository.UserRepository
import kotlinx.coroutines.experimental.withContext

class UserRepositoryImp(private val firestore: FirebaseFirestore,
                        private val mapper: EntityMapper<User, UserEntity>,
                        private val firestoreHelper: FirestoreHelper,
                        private val dispatcher: CoroutineDispatcherProvider): UserRepository {

    override suspend fun getUser(userId: String): Result<Error, User> {
        val task = firestore.collection(COLLECTION_USER)
                .document(userId)
                .get()
        val userEntity = try {
            withContext(dispatcher.io){
                firestoreHelper.tryTask(task)
            }.toObject(UserEntity::class.java)
        }catch (e :Error){
            return Result.buildError(e)
        }

        return userEntity?.let {
            Result.buildValue { mapper.mapFromEntity(it) }
        } ?: Result.buildError(FirestoreError.NotFound)
    }

    companion object {
        @Volatile private var INSTANCE: UserRepositoryImp? = null

        fun getInstance(firestore: FirebaseFirestore,
                        mapper: EntityMapper<User, UserEntity>,
                        firestoreHelper: FirestoreHelper,
                        dispatcher: CoroutineDispatcherProvider): UserRepositoryImp {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: UserRepositoryImp(firestore,
                        mapper,
                        firestoreHelper,
                        dispatcher).also { INSTANCE = it }
            }
        }
    }
}