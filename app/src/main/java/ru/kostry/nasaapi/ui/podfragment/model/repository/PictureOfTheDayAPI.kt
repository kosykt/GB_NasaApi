package ru.kostry.nasaapi.ui.podfragment.model.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kostry.nasaapi.ui.podfragment.model.data.PODServerResponseData

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String,
    ): Call<PODServerResponseData>
}