package com.app.flowexample.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.app.flowexample.common.utils.DataState
import com.app.flowexample.common.utils.ErrorModel
import com.app.flowexample.common.utils.Mapper
import com.app.flowexample.common.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> handleInto(
        stateFlow: MutableStateFlow<State<T?>>,
        block: suspend () -> DataState<T?>
    )
    {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                stateFlow.value = block().toState()
            } catch (e: Exception) {
                stateFlow.value = State.error(ErrorModel.getLocalError(e.localizedMessage ?: ""))
            }
        }
    }

    fun <T, R> handleInto(
        stateFlow: MutableStateFlow<State<R>>,
        block: suspend () -> DataState<T?>,
        mapper: Mapper<T?, R>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val state = block()) {
                    is DataState.Success -> stateFlow.value = State.successes(mapper(state.data))
                    is DataState.Error -> stateFlow.value = State.error(state.errorModel)
                }
            } catch (e: Exception) {
                stateFlow.value = State.error(ErrorModel.getLocalError(e.localizedMessage ?: ""))
            }
        }
    }

    @InternalCoroutinesApi
    fun <T> SavedStateHandle.getStateFlow(key: String, initValue: T): MutableStateFlow<T> {
        val savedStateHandle = this
        val mutableFlow = MutableStateFlow(savedStateHandle[key] ?: initValue)

        viewModelScope.launch {
            mutableFlow.collect {
                savedStateHandle[key] = it
            }
        }

        viewModelScope.launch {
            savedStateHandle.getLiveData<T>(key).asFlow().collect {
                mutableFlow.value = it
            }
        }

        return mutableFlow
    }
}