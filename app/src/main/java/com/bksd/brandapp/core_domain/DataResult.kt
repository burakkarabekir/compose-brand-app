package com.bksd.brandapp.core_domain

sealed interface DataResult<out D, out E : Error> {
    data class Success<out D>(val data: D) : DataResult<D, Nothing>
    data class Failure<out E : Error>(val error: E) : DataResult<Nothing, E>
}

inline fun <T, E : Error, R> DataResult<T, E>.map(map: (T) -> R): DataResult<R, E> {
    return when (this) {
        is DataResult.Failure -> DataResult.Failure(error)
        is DataResult.Success -> DataResult.Success(map(data))
    }
}

fun <T, E : Error> DataResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : Error> DataResult<T, E>.onSuccess(action: (T) -> Unit): DataResult<T, E> {
    return when (this) {
        is DataResult.Failure -> this
        is DataResult.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : Error> DataResult<T, E>.onFailure(action: (E) -> Unit): DataResult<T, E> {
    return when (this) {
        is DataResult.Failure -> {
            action(error)
            this
        }

        is DataResult.Success -> this
    }
}

typealias EmptyResult<E> = DataResult<Unit, E>