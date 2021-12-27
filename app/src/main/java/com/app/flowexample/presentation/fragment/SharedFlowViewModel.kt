package com.app.flowexample.presentation.fragment

import androidx.lifecycle.viewModelScope
import com.app.flowexample.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//TODO: check hash
@HiltViewModel
class SharedFlowViewModel @Inject constructor(

): BaseViewModel() {

    //*3
    private val _sharedFlow = MutableSharedFlow<Int>(replay = 1)
    private val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        sharedFlowTest()
    }

    private fun squareNumber(number: Int) {
        viewModelScope.launch {
            _sharedFlow.emit(number*number)
        }
    }

    /**
     * С помощю squareNumber эмитим значения в шаред флоу;
     * Два раза коллектим результат (симулируем несколько слушателей из одного источнка)
     */
    private fun sharedFlowTest() {
        /**
         * *1
         * Результат будет приходит в зависимости от размер буффера 'replay' в MutableSharedFlow
         */
        squareNumber(1)
        squareNumber(2)

        viewModelScope.launch {
            sharedFlow.collect {
                Timber.i("Result from first collector: $it")
            }
            /**
             * Если будем вызывать коллект внутри скоупа который уже коллектит - ничего в итоге не соберется
             * это связано с тем что шаред флоу коллект оператор будет бесконечно собирать значения, и никогда не закончится
             */
            sharedFlow.collect {
                Timber.i("Result from second collector: $it")
            }
        }
        /**
         * То есть что бы получать данные через оператор collect для каждого слушателя нужно открывать свой скоуп
         */
        viewModelScope.launch {
            sharedFlow.collect {
                Timber.i("Result from second collector: $it")
            }
        }
        // *2
        squareNumber(3)

        viewModelScope.launch {
            sharedFlow.collect {
                Timber.i("Result from third collector: $it")
            }
        }
    }
    /**
     * 1. В результате ничего не приходит потому что мы вызываем эмит до того как подписываемся на флоу
     * 2. Если эмитим после подписки то все работает как часы
     * 3. В этом случае мы указываем кол-во кэшируемых значений, и можем пихать значения до появления подписчика
     */

    //TODO: check flow debounce

}