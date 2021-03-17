package com.sudhindra.spiderinductionstask2.apis

import com.sudhindra.spiderinductionstask2.models.APOD
import retrofit2.http.GET
import retrofit2.http.Query

interface APODApi {
    @GET("apod")
    suspend fun getAPODForToday(@Query("api_key") apiKey: String): APOD

    @GET("apod")
    suspend fun getAPOD(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): APOD
}
