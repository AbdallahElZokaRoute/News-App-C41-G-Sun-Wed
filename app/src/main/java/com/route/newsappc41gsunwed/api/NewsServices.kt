package com.route.newsappc41gsunwed.api

import com.route.newsappc41gsunwed.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {

    @GET("top-headlines/sources")
    fun getSources(@Query("apiKey") apiKey: String = ApiManager.API_KEY): Call<SourcesResponse>
}
