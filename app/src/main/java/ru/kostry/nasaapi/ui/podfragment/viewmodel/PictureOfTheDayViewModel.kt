package ru.kostry.nasaapi.ui.podfragment.viewmodel

import android.util.Log
import android.widget.Toast
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
    
    private var _uri = MutableLiveData<String>()
    val uri = _uri

    private var _title = MutableLiveData<String>()
    val title = _title

    private var _text = MutableLiveData<String>()
    val text = _text
    
    private fun saveResponseStrings(success: PODAppState.Success<PODServerResponseData>) {
        _uri.value = success.stateData.url!!
        _title.value = success.stateData.title!!
        _text.value = success.stateData.explanation!!

    }

    init {
        sendServerRequest()
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
                    saveResponseStrings(PODAppState.Success(response.body()!!))
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