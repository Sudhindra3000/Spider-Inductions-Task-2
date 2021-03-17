package com.sudhindra.spiderinductionstask2.apis;

import com.sudhindra.spiderinductionstask2.models.APOD;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APODApi {

    @GET("apod")
    Call<APOD> getAPODForToday(@Query("api_key") String apiKey);

    @GET("apod")
    Call<APOD> getAPOD(@Query("date") String date, @Query("api_key") String apiKey);
}
