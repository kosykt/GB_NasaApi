package ru.kostry.nasaapi.ui.podfragment.model

import ru.kostry.nasaapi.ui.podfragment.model.data.PODServerResponseData

sealed class PODAppState {
    data class Success(val serverResponseData: PODServerResponseData) : PODAppState()
    data class Error(val error: Throwable) : PODAppState()
    data class Loading(val progress: Int?) : PODAppState()
}