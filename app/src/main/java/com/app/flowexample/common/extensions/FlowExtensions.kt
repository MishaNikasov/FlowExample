package com.app.flowexample.common.extensions

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun LifecycleOwner.addRepeatingJob(
    state: Lifecycle.State,
    controlContext: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(controlContext) {
        lifecycle.repeatOnLifecycle(state, block)
    }
}

inline fun <T> Flow<T>.collectWhenStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
        collect(action)
    }
}

fun <T> MutableStateFlow<MutableList<T>>.addItem(item: T) {
    val list = this.value
    list.add(item)
    this.value = list
}

fun <T> MutableStateFlow<MutableList<T>>.removeItem(index: Int) {
    val list = this.value
    list.removeAt(index)
    this.value = list
}

/**
 *
 * Without extensions will look like ->
 *
 *  lifecycleScope.launch {
 *      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
 *          viewModel.stateFlow
 *              //.flowWithLifecycle(lifecycle)
 *              // to avoid repeatOnLifecycle
 *              .collect { }
 *      }
 *  }
**/

