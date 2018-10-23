package com.lambadam.domain.model

import com.lambadam.domain.exception.Error

sealed class Result<out E, out V> {

    data class Value<out V>(val value: V) : Result<Nothing, V>()
    data class Failure<out E>(val failure: E) : Result<E, Nothing>()

    fun result(fnE: (E) -> Any, fnV: (V) -> Any): Any{
        return when(this){
            is Failure -> fnE(failure)
            is Value -> fnV(value)
        }
    }

    companion object{
        inline fun <V> buildValue(function: () -> V): Result<Nothing, V> {
            return Value(function())
        }

        fun buildError(error: Error): Result<Error, Nothing> {
            return Failure(error)
        }
    }
}

inline fun <T> wrapIntoResult(fn: () -> T): Result<Error,T> {
    return try {
        Result.buildValue { fn() }
    } catch (e: Exception){
        Result.buildError(Error.Unknown)
    }
}