package com.lambadam.domain.executor

import kotlinx.coroutines.experimental.CoroutineDispatcher

interface CoroutineDispatcherProvider {

    val io: CoroutineDispatcher
    val ui: CoroutineDispatcher
}