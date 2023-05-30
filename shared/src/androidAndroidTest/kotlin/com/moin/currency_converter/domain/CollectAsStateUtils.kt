package com.moin.currency_converter.com.moin.currency_converter.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.TestCoroutineScope

suspend fun <T> Flow<T>.testCollectAsState(): State<T?> {
    val stateFlow = MutableStateFlow<T?>(null)
       launchIn(TestCoroutineScope())
        collect { stateFlow.value = it }
    return stateFlow.asStateFlow().testAsState()
}

fun <T> StateFlow<T>.testAsState(): State<T> {
    val state = this.value
    return object : State<T> {
        override val value: T
            get() = state
    }
}

interface State<T> {
    val value: T
}
