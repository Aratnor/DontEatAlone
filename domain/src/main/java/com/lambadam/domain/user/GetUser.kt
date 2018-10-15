package com.lambadam.domain.user

import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.interactor.UseCase
import com.lambadam.domain.model.User
import com.lambadam.domain.repository.UserRepository
import com.lambadam.domain.user.GetUser.Params

class GetUser(private val userRepository: UserRepository,
              dispatcherProvider: CoroutineDispatcherProvider)
    : UseCase<User, Params>(dispatcherProvider){

    override suspend fun buildUseCase(params: Params) = userRepository.getUser(params.userId)

    data class Params(val userId: String)
}


