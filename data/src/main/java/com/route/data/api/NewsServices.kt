package com.route.data.api

import com.route.data.api.model.NewsResponse
import com.route.data.api.model.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") categoryId: String,
        @Query("apiKey") apiKey: String = "8e30e66ecc364d75967401f639e6f535"
    ): Response<SourcesResponse>

    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String = "8e30e66ecc364d75967401f639e6f535"
    ): Response<NewsResponse>
}
