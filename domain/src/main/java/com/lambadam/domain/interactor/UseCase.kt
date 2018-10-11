package com.lambadam.domain.interactor

import com.lambadam.domain.model.Result
import kotlinx.coroutines.experimental.*

abstract class UseCase<out T, in Params> {

    abstract fun buildUseCase(params: Params?): Result<Exception, T>

    suspend fun execute(params: Params? = null,
                        handleResult: (Result<Exception, T>) -> Unit = {}) = coroutineScope {
        val job = async(Dispatchers.Default) {  buildUseCase(params) }
        launch(Dispatchers.Main) {  handleResult(job.await()) }
    }
}