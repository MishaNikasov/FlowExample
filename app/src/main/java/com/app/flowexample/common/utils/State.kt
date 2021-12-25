package com.app.flowexample.common.utils

sealed class State<out T> {

    data class Successes<out T>(val data: T) : State<T>()
    data class Error<out T>(val errorModel: ErrorModel, val data: T? = null) : State<T>()
    data class Loading<out T>(val data: T? = null) : State<T>()
    object Empty: State<Nothing>()

    companion object {
        fun <T> successes(data: T) = Successes(data)
        fun <T> error(errorModel: ErrorModel, data: T? = null) = Error(errorModel, data)
        fun <T> loading(data: T? = null) = Loading(data)
    }

    fun getResult(): T? {
        return when (this) {
            is Successes -> this.data
            else -> null
        }
    }

    fun getResult(block: (T) -> Unit) {
        if (this is Successes) {
             block(this.data)
        }
    }

    fun getResult(
        loading: () -> Unit,
        successes: (T) -> Unit,
        error: (ErrorModel) -> Unit
    ) {
        when (this) {
            is Loading -> loading()
            is Successes -> successes(data)
            is Error -> error(errorModel)
            else -> Unit
        }
    }
}