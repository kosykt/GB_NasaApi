package ru.kostry.nasaapi.ui.podfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kostry.nasaapi.BuildConfig
import ru.kostry.nasaapi.model.PODAppState
import ru.kostry.nasaapi.ui.podfragment.model.data.PODServerResponseData
import ru.kostry.nasaapi.ui.podfragment.model.repository.PODRetrofitImpl

class PictureOfTheDayViewModel : ViewModel() {

    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
    private val liveDataForViewToObserve: MutableLiveData<PODAppState> = MutableLiveData()

    private val _positionFABisCenter = MutableLiveData<Boolean>()
    var positionFABisCenter = _positionFABisCenter

    private val _uri = MutableLiveData<String>()
    var uri = _uri

    private val _title = MutableLiveData<String>()
    var title = _title

    private val _explanation = MutableLiveData<String>()
    var explanation = _explanation

    private fun saveResponseStrings(success: PODServerResponseData?) {
        _uri.value = success?.url!!
        _title.value = success.title.toString()
        _explanation.value = success.explanation.toString()
    }

    init {
        sendServerRequest()
    }

    fun setPositionFABisCenter(position: Boolean) {
        _positionFABisCenter.value = position
    }

    private fun sendServerRequest() {
        val apiKey: String = BuildConfig.NASA_API_KEY
        retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
            Callback<PODServerResponseData> {
            override fun onResponse(
                call: Call<PODServerResponseData>,
                response: Response<PODServerResponseData>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    saveResponseStrings(response.body())
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