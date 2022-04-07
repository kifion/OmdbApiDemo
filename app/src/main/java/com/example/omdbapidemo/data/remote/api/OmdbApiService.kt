package com.example.omdbapidemo.data.remote.api

import com.example.omdbapidemo.data.remote.response.MovieDetailResponse
import com.example.omdbapidemo.data.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun getMovieListByText(
        @Query("s") text: String,
    ): SearchResponse

    @GET("/")
    suspend fun getMovieDetailById(
        @Query("i") text: String,
    ): MovieDetailResponse
}