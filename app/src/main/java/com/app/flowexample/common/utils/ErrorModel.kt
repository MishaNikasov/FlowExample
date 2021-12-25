package com.app.flowexample.common.utils

import com.google.gson.Gson
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

data class ErrorModel(
    var error: String = ""
) {

    companion object {
        fun getLocalError(msg: String) = ErrorModel(error = msg)
        fun emptyBodyError() = ErrorModel(error = "Empty response")
    }

    @JvmName("funGetErrorMessage")
    fun getErrorMessage(): String = error
}

fun List<ErrorModel>.getCommonErrorModel(): ErrorModel {
    return ErrorModel(
        error = this.joinToString { it.getErrorMessage() }
    )
}

fun createErrorModelResponseBody(msg: String): ResponseBody {
    return Gson().toJson(ErrorModel.getLocalError(msg)).toResponseBody()
}

/**
 * For retrofit response
 */
fun Response<*>?.getErrorModel(): ErrorModel {
    try {
        this?.errorBody()?.let { response ->
            val errorModel = Gson().fromJson(response.string(), ErrorModel::class.java)
            return errorModel ?: ErrorModel()
        }
        return ErrorModel()
    } catch (e: Exception) {
        return ErrorModel()
    }
}

/**
 * For okhttp response
 */
fun okhttp3.Response?.getErrorModel(): ErrorModel {
    try {
        this?.peekBody(2048)?.let { response ->
            val errorModel = Gson().fromJson(response.string(), ErrorModel::class.java)
            return errorModel ?: ErrorModel()
        }
        return ErrorModel()
    } catch (e: Exception) {
        return ErrorModel()
    }
}
