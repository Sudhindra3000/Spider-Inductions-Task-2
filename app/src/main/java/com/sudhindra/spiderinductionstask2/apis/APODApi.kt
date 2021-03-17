package com.sudhindra.spiderinductionstask2.apis

import com.sudhindra.spiderinductionstask2.models.APOD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APODApi {
    @GET("apod")
    fun getAPODForToday(@Query("api_key") apiKey: String?): Call<APOD?>?

    @GET("apod")
    fun getAPOD(@Query("date") date: String?, @Query("api_key") apiKey: String?): Call<APOD?>?
}
