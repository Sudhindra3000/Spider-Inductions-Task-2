package com.sudhindra.spiderinductionstask2.apis

import com.sudhindra.spiderinductionstask2.models.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search")
    suspend fun getSearchResult(@Query("q") query: String?, @Query("page") page: Int): SearchResult
}
