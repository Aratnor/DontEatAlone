package com.lambadam.domain.model

sealed class Result<out E, out V> {

    data class Value<out V>(val value: V) : Result<Nothing, V>()
    data class Error<out E>(val error: E) : Result<E, Nothing>()

    companion object{
        inline fun <V> buildValue(function: () -> V): Result<Nothing, V> {
            return Value(function())
        }

        fun buildError(error: Exception): Result<Exception, Nothing> {
            return Error(error)
        }
    }
}