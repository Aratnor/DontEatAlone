package com.lambadam.donteatalone.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambadam.domain.exception.Error
import com.lambadam.domain.executor.CoroutineDispatcherProvider
import com.lambadam.donteatalone.R
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseViewModel(private val dispatcher: CoroutineDispatcherProvider)
    : ViewModel(), CoroutineScope {

    private val job =  Job()
    private val _failure = MutableLiveData<Error>()

    val failure: LiveData<Error>
        get() = _failure

    override val coroutineContext: CoroutineContext
        get() = dispatcher.ui + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun handleFailure(error: Error){
        _failure.value = error
    }
}