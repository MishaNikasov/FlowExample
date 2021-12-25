package com.app.flowexample.presentation.base

interface IBinder<T> {
    fun bindView(model: T?, position: Int)
}