package com.example.omdbapidemo.data.remote.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddQueryParamInterceptor(private val queryParameters: Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
        for (queryParameter in queryParameters) {
            url.addQueryParameter(queryParameter.key, queryParameter.value)
        }
        val buildUrl = url.build()

        val requestBuilder: Request.Builder = original.newBuilder().url(buildUrl)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}