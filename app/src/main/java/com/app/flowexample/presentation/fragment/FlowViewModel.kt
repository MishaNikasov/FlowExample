package com.app.flowexample.presentation.fragment

import androidx.lifecycle.viewModelScope
import com.app.flowexample.common.utils.State
import com.app.flowexample.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class FlowViewModel : BaseViewModel() {

    init {
//        collectFlows()
//        operatorTest()
        combineTest()
    }

    private fun getFlowOfIntResult() = flow {
        delay(500)
        emit(1)
        delay(500)
        emit(2)
        delay(500)
        emit(3)
    }

    private fun getFlowOfStringResult() = flow {
        delay(500)
        emit("Str 1")
        delay(500)
        emit("Str 2")
        delay(500)
        emit("Str 3")
    }

    private fun collectFlows() {
        viewModelScope.launch {
            /**
             * У флоу есть 2 вида операторов
             * - обычные (filter, map и тд.) они последовательны и выполняются до вызова терминального оператора
             * - терминальные (collect и тд.) в случае с холодным флоу мы стартуем соответсвенный
             *   в случае если флоу горячий, мы подписываемся на него
             */
            getFlowOfIntResult()
                .map { it * 10 }
                .filter { it > 10 }
                .collect {
                    Timber.i("Int flow value: $it")
                }
            /**
             * Мы запускаем оба флоу в одном скоуп, и за счет того что flow - холодный
             * он конечный, и не блочик скоуп
             */
            getFlowOfStringResult().collect {
                Timber.i("String flow value: $it")
            }
        }
    }

    /**
     * Холодный флоу можно превратить в горячий (двух типов)
     * операторами stateIn(); sharedIn() соответственно
     */
    val simpleStateFlow = flow {
        emit(3)
        delay(500)
        emit(10)
    }
        .map { it * 10 }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    private val simpleSharedFlow = flow {
        emit(3)
        delay(500)
        emit(10)
    }
        .map { it * 10 }
        .shareIn(viewModelScope, SharingStarted.Lazily)


    /**
     * Разница терминальных операторов
     */
    private fun operatorTest() {
        viewModelScope.launch {
            getFlowOfIntResult().collect {
                delay(700)
                Timber.i("Value with collect: $it")
            }
            getFlowOfIntResult().collectLatest {
                delay(700)
                Timber.i("Value with collectLatest: $it")
            }
        }
    }

    /**
     * Комбинирование нескольких флоу
     */
    private fun combineTest() {
        getFlowOfIntResult().zip(getFlowOfStringResult()) { i, s ->
            Timber.i("ZIP: $i; $s")
        }
        getFlowOfIntResult().combine(getFlowOfStringResult()) { i, s ->
            Timber.i("COMBINE: $i; $s")
        }

        viewModelScope.launch {
            getFlowOfIntResult().collect()
        }
    }

    /**
     * Flat map`ы на примере поиска
     */
    private val DELAY_TIME = 500L

    private val _searchQuery = MutableSharedFlow<String>(1)

    /**
     * searchResult - flow в котором мы храним обработанный результат из _searchQuery
     *
     * _searchQuery.flatMapLatest - берет значение из поля для search query, и начинает обработку внутри блока;
     *
     * flatMapLatest - работает по аналогии с collectLatest, если продюсер (_searchQuery) отдает значение быстрее чем они успевают
     * обработаться в блоке, необработтанные скопы кенселяться
     */
    val searchResult = _searchQuery.flatMapLatest {
        /**
         * Симулируем debounce:
         * При вызове функции перед самим реквестом ставим задержку, что бы не фейерить каждое измененеие
         */
        delay(DELAY_TIME)
        simulateSearchRequest(it)
    }.stateIn(viewModelScope, SharingStarted.Lazily, State.Empty)

    fun search(value: String) {
        viewModelScope.launch {
            _searchQuery.emit(value)
        }
    }

    private fun simulateSearchRequest(value: String) = flow {
        emit(State.loading())
        Timber.i("Request for '$value' started")
        delay(1000)
        emit(State.successes("Search result of: '$value'"))
    }

    /**
     * Один бог знает что тут происходит
     */
    fun wtf() {

    }
}