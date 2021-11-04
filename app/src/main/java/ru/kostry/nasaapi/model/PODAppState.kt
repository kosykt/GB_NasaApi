package ru.kostry.nasaapi.model

sealed class PODAppState {
    data class Success<out T>(val stateData: T): PODAppState()
    data class Error(val error: Throwable) : PODAppState()
    data class Loading(val progress: Int?) : PODAppState()
}