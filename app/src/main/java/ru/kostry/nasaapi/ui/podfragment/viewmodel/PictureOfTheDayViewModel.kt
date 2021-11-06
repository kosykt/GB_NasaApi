package ru.kostry.nasaapi.ui.podfragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kostry.nasaapi.BuildConfig
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.ui.podfragment.model.data.PODServerResponseData
import ru.kostry.nasaapi.ui.podfragment.model.repository.PODRetrofitImpl

enum class PODApiStatus { LOADING, ERROR, DONE }

class PictureOfTheDayViewModel : ViewModel() {

    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()

    private val _status = MutableLiveData<PODApiStatus>()
    val status: LiveData<PODApiStatus> = _status

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _uri = MutableLiveData<String>()
    val uri = _uri

    private val _title = MutableLiveData<String>()
    val title = _title

    private val _explanation = MutableLiveData<String>()
    val explanation = _explanation

    private val _date = MutableLiveData<String>()
    val date = _date

    private fun saveResponseStrings(success: PODServerResponseData?) {
        _uri.value = success?.url!!
        _title.value = success.title.toString()
        _explanation.value = success.explanation.toString()
        _date.value = success.date.toString()
    }

    init {
        sendServerRequest()
    }

    private fun sendServerRequest() {
        _status.value = PODApiStatus.LOADING
        val apiKey: String = BuildConfig.NASA_API_KEY
        retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
            Callback<PODServerResponseData> {
            override fun onResponse(
                call: Call<PODServerResponseData>,
                response: Response<PODServerResponseData>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    saveResponseStrings(response.body())
                    _status.value = PODApiStatus.DONE
                } else {
                    val message = response.message()
                    _status.value = PODApiStatus.ERROR
                    if (message.isNullOrEmpty()) {
                        _status.value = PODApiStatus.ERROR
                        _errorMessage.value = R.string.empty_response.toString()
                    } else {
                        _status.value = PODApiStatus.ERROR
                        _errorMessage.value = message
                    }
                }
            }

            override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                _status.value = PODApiStatus.ERROR
                _errorMessage.value = t.message
            }
        })
    }
}