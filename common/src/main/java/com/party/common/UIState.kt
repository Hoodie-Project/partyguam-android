package com.party.common

import androidx.compose.runtime.Immutable

sealed class UIState<out T>(
    val data: T? = null,
){
    data object Idle: UIState<Nothing>()
    data object Loading: UIState<Nothing>()
    class Success<T>(data: T): UIState<T>(data)
    class Error<T>(data: T? = null): UIState<T>(data)
}