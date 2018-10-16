package com.lambadam.donteatalone.user_profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.user.GetUser
import com.lambadam.donteatalone.Injection

class UserProfileModelFactory(private val getUser: GetUser,
                              private val dispatcherProvider: CoroutineDispatcherProvider)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(!modelClass.isAssignableFrom(UserProfileModel::class.java)) {
            throw IllegalArgumentException("Model class must be TasksViewModel")
        }
        @Suppress("UNCHECKED_CAST")
        return UserProfileModel(getUser,dispatcherProvider)as T
    }
    companion object {
        private var INSTANCE: UserProfileModelFactory? = null

        fun getInstance(context: Context): UserProfileModelFactory {
            return UserProfileModelFactory.INSTANCE ?: synchronized(this){
                UserProfileModelFactory.INSTANCE ?: UserProfileModelFactory(
                        Injection.provideGetUser(context),
                        Injection.dispatcherProvider)
                        .also { UserProfileModelFactory.INSTANCE = it }
            }

        }
    }

}

