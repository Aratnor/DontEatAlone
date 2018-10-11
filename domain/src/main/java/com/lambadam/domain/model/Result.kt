package com.lambadam.domain.model

sealed class Result<out E, out V> {

    data class Value<out V>(val value: V) : Result<Nothing, V>()
    data class Error<out E>(val error: E) : Result<E, Nothing>()

    fun result(fnE: (E) -> Any, fnV: (V) -> Any): Any{
        return when(this){
            is Error -> fnE(error)
            is Value -> fnV(value)
        }
    }

    companion object{
        inline fun <V> buildValue(function: () -> V): Result<Nothing, V> {
            return Value(function())
        }

        fun buildError(error: Exception): Result<Exception, Nothing> {
            return Error(error)
        }
    }
}