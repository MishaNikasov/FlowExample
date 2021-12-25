package com.app.flowexample.common.utils

sealed class DataState<out R> {

    data class Success<out T>(val data: T?) : DataState<T>()
    data class Error(val errorModel: ErrorModel) : DataState<Nothing>()

    companion object {
        fun <T> successes(data: T?) = Success(data)
        fun error(errorModel: ErrorModel) = Error(errorModel)
    }

    fun toState() = when (this) {
        is Success -> State.successes(this.data)
        is Error -> State.error(this.errorModel)
    }

    fun getResult(): R? {
        return if (this is Success) this.data else null
    }

    fun getResult(
        successesBlock: (R?) -> Unit,
        errorBlock: ((ErrorModel) -> Unit)? = null
    ) {
        when (this) {
            is Success -> successesBlock(data)
            is Error -> errorBlock?.invoke(errorModel)
        }
    }
}