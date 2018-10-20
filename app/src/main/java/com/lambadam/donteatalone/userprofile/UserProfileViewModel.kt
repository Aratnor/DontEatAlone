package com.lambadam.donteatalone.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.model.User
import com.lambadam.domain.user.GetUser
import com.lambadam.domain.user.GetUser.Params
import com.lambadam.donteatalone.base.BaseViewModel
import kotlinx.coroutines.experimental.launch

class UserProfileViewModel(private val getUser: GetUser,
                           dispatcher: CoroutineDispatcherProvider)
    : BaseViewModel(dispatcher) {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun loadUser(userId: String) = launch {
        getUser.execute(Params(userId)) { result({handleFailure()},::loadUserSuccess)}
    }

    private fun loadUserSuccess(user: User) {
        _user.value = user
    }
}