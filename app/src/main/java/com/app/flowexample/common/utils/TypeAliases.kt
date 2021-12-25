package com.app.flowexample.common.utils

import kotlinx.coroutines.flow.MutableStateFlow

typealias MutableState<T> = MutableStateFlow<State<T>>
typealias Mapper<Input, Output> = (Input) -> Output
