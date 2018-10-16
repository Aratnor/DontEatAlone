package com.lambadam.donteatalone.user_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.model.None
import com.lambadam.domain.model.User
import com.lambadam.domain.user.GetUser
import com.lambadam.domain.user.GetUser.Params
import com.lambadam.donteatalone.base.BaseViewModel
import kotlinx.coroutines.experimental.launch

class UserProfileViewModel(private val getUserUseCase: GetUser,
                           dispatcher: CoroutineDispatcherProvider)
    : BaseViewModel(dispatcher) {
    private val _getUser = MutableLiveData<User>()
    val getUser : LiveData<User>

    get() = _getUser

    fun getUser(userId: String) = launch {
        getUserUseCase.execute(Params(userId)) { it.result({handleFailure()},::getUserSuccess)}
    }

    private fun getUserSuccess(user: User) {
        _getUser.value = user
    }
}