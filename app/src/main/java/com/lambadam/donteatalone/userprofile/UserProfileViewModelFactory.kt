package com.lambadam.donteatalone.userprofile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.user.GetUser
import com.lambadam.donteatalone.Injection

class UserProfileViewModelFactory(private val getUser: GetUser,
                                  private val dispatcherProvider: CoroutineDispatcherProvider)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(!modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            throw IllegalArgumentException("Model class must be UserProfileViewModel")
        }
        @Suppress("UNCHECKED_CAST")
        return UserProfileViewModel(getUser,dispatcherProvider)as T
    }
    companion object {
        private var INSTANCE: UserProfileViewModelFactory? = null

        fun getInstance(context: Context): UserProfileViewModelFactory {
            return UserProfileViewModelFactory.INSTANCE ?: synchronized(this){
                UserProfileViewModelFactory.INSTANCE ?: UserProfileViewModelFactory(
                        Injection.provideGetUser(context),
                        Injection.dispatcherProvider)
                        .also { UserProfileViewModelFactory.INSTANCE = it }
            }

        }
    }

}

