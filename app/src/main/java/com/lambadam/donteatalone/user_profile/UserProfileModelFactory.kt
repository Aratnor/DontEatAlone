package com.lambadam.donteatalone.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.user.GetUser

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

}

