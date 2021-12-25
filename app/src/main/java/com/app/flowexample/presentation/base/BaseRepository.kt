package com.app.flowexample.presentation.base

import com.app.flowexample.common.utils.DataState
import com.app.flowexample.common.utils.ErrorModel
import com.app.flowexample.common.utils.Mapper
import com.app.flowexample.common.utils.getErrorModel
import retrofit2.Response

open class BaseRepository {

    fun <DTO, Model> obtainResponse(
        request: Response<DTO?>,
        mapper: Mapper<DTO?, Model?>
    ): DataState<Model> {
        return try {
            val mappedResponseBody = mapper(request.body())
            if (request.isSuccessful) {
                mappedResponseBody?.let {
                    DataState.successes(mappedResponseBody)
                } ?: DataState.error(ErrorModel.getLocalError("Empty body"))
            } else {
                DataState.error(request.getErrorModel())
            }
        } catch (e: Exception) {
            DataState.error(ErrorModel.getLocalError(e.localizedMessage ?: "Something went wrong"))
        }
    }

}