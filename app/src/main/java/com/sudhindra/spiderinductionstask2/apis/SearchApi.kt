package com.sudhindra.spiderinductionstask2.apis;

import com.sudhindra.spiderinductionstask2.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {

    @GET("search")
    Call<SearchResult> getSearchResult(@Query("q") String query, @Query("page") int page);
}
