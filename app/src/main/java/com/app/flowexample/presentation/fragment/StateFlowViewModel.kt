package com.app.flowexample.presentation.fragment

import androidx.lifecycle.viewModelScope
import com.app.flowexample.common.utils.State
import com.app.flowexample.domain.model.Event
import com.app.flowexample.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StateFlowViewModel @Inject constructor(

): BaseViewModel() {

    private val _stateFlowUi = MutableStateFlow<State<Event>>(State.loading())
    val stateFlowUi = _stateFlowUi.asStateFlow()

    fun sendStateEvent() {
        viewModelScope.launch {
            delay(1000)
            _stateFlowUi.emit(State.successes(Event("STATE flow event")))
        }
    }

    private val _sharedFlowUi = MutableSharedFlow<State<Event>>(replay = 0)
    val sharedFlowUi = _sharedFlowUi.asSharedFlow()

    fun sendSharedEvent() {
        viewModelScope.launch {
            delay(1000)
            _sharedFlowUi.emit(State.successes(Event("SHARED flow event")))
        }
    }

}