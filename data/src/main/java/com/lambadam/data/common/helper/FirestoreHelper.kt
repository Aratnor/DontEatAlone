package com.lambadam.data.common.helper

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.*
import com.lambadam.data.exception.FirestoreError
import com.lambadam.domain.exception.Error
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.tasks.await


class FirestoreHelper{

    @Throws(Error::class)
    suspend fun <T> tryTask(task: Task<T>): T {
        return try {
            task.await()
        } catch (e: FirebaseFirestoreException){

            when(e.code){

                UNAVAILABLE, DEADLINE_EXCEEDED -> {

                    try {
                        retryOperation{ task.await()}
                    }catch (e: FirebaseFirestoreException){
                        throw FirestoreError.Unavailable
                    }
                }
                NOT_FOUND -> throw FirestoreError.NotFound
                PERMISSION_DENIED -> throw FirestoreError.PermissionDenied
                else -> throw Error.Unknown
            }
        }
    }

    private suspend fun <T> retryOperation(times: Int = 3,
                                           delayTime: Long = 500,
                                           operation: suspend () -> T): T {
        repeat(times){
            try {
                return operation()
            }catch (e: FirebaseFirestoreException){
                Log.d("FirestoreHelper", e.toString())
            }
            delay(delayTime)
        }
        return operation()
    }
}