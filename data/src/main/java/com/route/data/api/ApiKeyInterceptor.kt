package com.route.data.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    val API_KEY = "apiKey"
    val API_KEY_VALUE = "8e30e66ecc364d75967401f639e6f535"
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val urlBuilder = request.url.newBuilder()
        urlBuilder.addQueryParameter(
            API_KEY,
            API_KEY_VALUE
        )
        val url = urlBuilder.build()
        requestBuilder.url(url)
        return chain.proceed(requestBuilder.build())
    }

}