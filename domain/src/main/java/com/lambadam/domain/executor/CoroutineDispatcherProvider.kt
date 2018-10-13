package com.lambadam.domain.executor

import kotlinx.coroutines.experimental.CoroutineDispatcher

interface CoroutineDispatcherProvider {

    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val ui: CoroutineDispatcher
}