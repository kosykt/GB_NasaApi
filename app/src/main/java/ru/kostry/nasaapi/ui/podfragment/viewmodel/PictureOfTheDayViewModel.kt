package ru.kostry.nasaapi.ui.podfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kostry.nasaapi.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kostry.nasaapi.ui.podfragment.model.PODAppState
import ru.kostry.nasaapi.ui.podfragment.model.data.PODServerResponseData
import ru.kostry.nasaapi.ui.podfragment.model.repository.PODRetrofitImpl

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PODAppState> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl())
    : ViewModel() {

    private var _onMainFAB = MutableLiveData<Boolean>()
    val onMainFAB: LiveData<Boolean> = _onMainFAB

    init {
        _onMainFAB.value = true
    }

    fun setOnMainFAB(position: Boolean){
        _onMainFAB.value = position
    }

    fun getData(): LiveData<PODAppState> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = PODAppState.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODAppState.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PODAppState.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PODAppState.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PODAppState.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PODAppState.Error(t)
                }
            })
        }
    }
}