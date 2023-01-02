package com.newsheadlines.app.arch

import com.newsheadlines.app.data.model.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewSourceApi {

    @GET(".")
    suspend fun topHeadlines(
        @Query("sources") query: String,
        @Query("apiKey") apiKey: String?
    ) : NewsListResponse

}