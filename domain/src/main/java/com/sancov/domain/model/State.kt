package com.sancov.domain.model

typealias EmptyState = State<Unit>

sealed class State<out T> {
    data class Success<T>(val value: T) : State<T>()
    data class Loading<T>(val value: T? = null) : State<T>()
    data class Error<T>(val cause: Throwable, val value: T? = null) : State<T>()
    class Empty<T> : State<T>()

    val data: T?
        get() = when (this) {
            is Success -> value
            is Loading -> value
            is Error -> value
            else -> null
        }

    val error: Throwable?
        get() = when (this) {
            is Error -> cause
            else -> null
        }

    val dropData: State<Unit>
        get() = when (this) {
            is Success -> Success(Unit)
            is Loading -> Loading(Unit)
            is Error -> Error(cause, Unit)
            is Empty -> Empty()
        }

    val isSuccess: Boolean get() = this is Success
    val isLoading: Boolean get() = this is Loading
    val isError: Boolean get() = this is Error
}