package com.lambadam.donteatalone.executor

import com.lambadam.domain.executor.CoroutineDispatcherProvider
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Dispatchers

class CoroutineDispatcherProviderImp: CoroutineDispatcherProvider {

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val ui: CoroutineDispatcher
        get() = Dispatchers.Main
}