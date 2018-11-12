package com.lambadam.domain.interactor

import com.lambadam.domain.exception.Error
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.domain.model.Result
import kotlinx.coroutines.experimental.*

abstract class UseCase<out T, in Params>(private val dispatcher: CoroutineDispatcherProvider) {

    abstract suspend fun buildUseCase(params: Params): Result<Error, T>

    suspend fun execute(params: Params, handleResult: Result<Error, T>.() -> Unit){
        val result = withContext(dispatcher.default){ buildUseCase(params) }
        handleResult(result)
    }

    class None
}