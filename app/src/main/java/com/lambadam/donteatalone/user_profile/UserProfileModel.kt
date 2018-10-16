package com.lambadam.donteatalone.user_profile

import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.user.GetUser
import com.lambadam.donteatalone.base.BaseViewModel

class UserProfileModel(private val getUser: GetUser,
                       dispatcher: CoroutineDispatcherProvider)
    : BaseViewModel(dispatcher) {

}